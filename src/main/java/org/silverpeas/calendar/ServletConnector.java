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
package org.silverpeas.calendar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.silverpeas.calendar.api.CalendarEntry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharEncoding;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;

/**
 * ServletConnector is an helper for the importation applets. It provides methods to communicate
 * with Silverpeas calendar importation servlet
 *
 * @authors Ludovic Bertin, Xavier Delorme
 */
public class ServletConnector {

  private String servletURL = null;

  /**
   * A ServletConnector is built given an url to Silverpeas calendar importation servlet
   *
   * @param servletUrl url to Silverpeas calendar importation servlet
   */
  public ServletConnector(String servletUrl) {
    this.servletURL = servletUrl;
  }

  /**
   * Send given journal headers to Silverpeas calendar importation servlet and generate a report (as
   * a String).
   *
   * @param headers List of JournalHeader objects to send to Silverpeas calendar importation servlet
   * @return a String object containing importation results (number of items added, modified and
   * deleted - or an error message)
   */
  public final String sendHeaders(List<CalendarEntry> headers) {
    String report = "";
    try {
      URLConnection con = getServletConnection();
      write(headers, con.getOutputStream());
      InputStream instr = con.getInputStream();
      report = IOUtils.toString(instr, CharEncoding.UTF_8);
      instr.close();
    } catch (MalformedURLException e) {
      report = "URL de la servlet de synchronisation incorrecte.";
      e.printStackTrace();
    } catch (IOException e) {
      report = "Probleme d'entree/sortie.";
      e.printStackTrace();
    }
    return report;
  }

  public void write(List<CalendarEntry> headers, OutputStream out) throws IOException {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(Feature.INDENT_OUTPUT, false);
      mapper.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, true);
      mapper.configure(Feature.USE_ANNOTATIONS, false);
      mapper.writeValue(out, headers);
    } finally {
      IOUtils.closeQuietly(out);
    }
  }

  /**
   * Open a connection to the Silverpeas calendar importation servlet.
   *
   * @return a URLConnection object to connect to the Silverpeas calendar importation servlet.
   * @throws MalformedURLException
   * @throws IOException
   */
  private final URLConnection getServletConnection() throws MalformedURLException, IOException {
    URL urlServlet = new URL(servletURL);
    URLConnection con = urlServlet.openConnection();
    con.setDoInput(true);
    con.setDoOutput(true);
    con.setUseCaches(false);
    con.setRequestProperty("Content-Type", "application/json");
    return con;
  }
}
