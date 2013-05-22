/**
 * Copyright (C) 2000 - 2012 Silverpeas
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * As a special exception to the terms and conditions of version 3.0 of the GPL, you may
 * redistribute this Program in connection with Free/Libre Open Source Software ("FLOSS")
 * applications as described in Silverpeas's FLOSS exception. You should have received a copy of the
 * text describing the FLOSS exception, and it is also available here:
 * "http://www.silverpeas.org/legal/licensing"
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package org.silverpeas.calendar.outlook;

import java.applet.Applet;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import org.silverpeas.calendar.AppletDrawer;
import org.silverpeas.calendar.ServletConnector;
import org.silverpeas.calendar.api.CalendarEntriesFactory;
import org.silverpeas.calendar.api.CalendarEntry;

import com.jacob.com.ComThread;
import com.jacob.com.LibraryLoader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * This applet uses Jacob library to read Outlook events and send them to Silverpeas
 * ImportCalendarServlet<br>
 * <br>
 * <ul>
 * Applet parameters are :
 * <li>SESSIONID : id of HTTP session where applet was called</li>
 * <li>SERVLETURL : ImportCalendarServlet url</li>
 * <li>NBDAYSBEFORE : number of days before current date for which event must be imported
 * </ul>
 *
 * @author Ludovic Bertin
 */
public class ImportEventsApplet extends Applet {

  private static final long serialVersionUID = 1L;

  private transient CalendarEntriesFactory journalList = null;

  private transient AppletDrawer appletDrawer = new AppletDrawer();

  private transient ServletConnector servletConnector = null;

  private int nbDaysBefore = 7;

  /**
   * default constructor
   *
   * @throws HeadlessException
   */
  public ImportEventsApplet() throws HeadlessException {
    super();
  }

  /**
   * Get applet information
   *
   * @return
   */
  @Override
  public String getAppletInfo() {
    return "Applet d'import de rendez-vous de Outlook vers Silverpeas";
  }

  /**
   * Applet initialization : load jacob library
   *
   * @see java.applet.Applet#init()
   */
  @Override
  public void init() {
    super.init();
    // first copy DLL in local and add it to path
    try {
      initLibrary();
    } catch (IOException e) {
      // Unable to copy/load the library
      e.printStackTrace();
    }
    journalList = new CalendarEntriesFactory(getParameter("SESSIONID"));    
    servletConnector = new ServletConnector(getParameter("SERVLETURL"));
    String paramDaysBefore = getParameter("NBDAYSBEFORE");
    if (paramDaysBefore != null) {
      nbDaysBefore = Integer.parseInt(getParameter("NBDAYSBEFORE"));
    }
  }

  /**
   * Load Jacob Library
   *
   * @throws IOException
   */
  private void initLibrary() throws IOException {
    Logger.getLogger("ImportEventsApplet").info("Copying " + LibraryLoader.getPreferredDLLName());
    File tempJacobDll = copyDll(LibraryLoader.getPreferredDLLName());
    System.setProperty(LibraryLoader.JACOB_DLL_PATH, tempJacobDll.getAbsolutePath());
  }

  private File copyDll(String fileName) throws IOException {
    InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName + ".dll");
    try {
      File dllFile = File.createTempFile(fileName, ".dll");
      FileUtils.copyInputStreamToFile(in, dllFile);
      return dllFile;
    } catch (IOException ioex) {
      IOUtils.closeQuietly(in);
      Logger.getLogger("ImportEventsApplet").throwing("ImportEventsApplet", "copyDll", ioex);
      throw ioex;
    }
  }

  /**
   * Do the importation stuff
   */
  @Override
  public void start() {
    try {
      super.start();
      // set applet size
      appletDrawer.initApplet(this, "Silverpeas : synchronization Outlook en cours\n");
      // Retrieve outlook event
      SilverOutlookEventsList myEvents = new SilverOutlookEventsList();
      myEvents.initWithCurrentOutlook();
      myEvents.loadItemsList(nbDaysBefore);
      // Initialize OutlookSecurityManager
      OutlookSecurityManager securityManager = null;
      try {
        securityManager = new OutlookSecurityManager(myEvents.getOOutlook());
        // Disable Outlook Security Warnings
        securityManager.setSecurityWarningsEnabled(false);
      } catch (Exception e) {
        e.printStackTrace();
      }
      // build list of JournalHeader objects
      List<CalendarEntry> headers = journalList.buildJournalHeadersList(myEvents);
      // Enable Outlook Security Warnings
      if (securityManager != null) {
        securityManager.setSecurityWarningsEnabled(true);
      }
      // send headers to Synchronization servlet
      String report = servletConnector.sendHeaders(headers);
      appletDrawer.addMessage(report);
      // finished
      appletDrawer.addMessage("Synchronisation terminee");
      // show report window
      if (!report.isEmpty()) {
        appletDrawer.showDialog();
      }
    } finally {
      // Release COM component and delete temporary DLL
      ComThread.Release();
      FileUtils.deleteQuietly(new File(System.getProperty(LibraryLoader.JACOB_DLL_PATH)));
    }
  }

}
