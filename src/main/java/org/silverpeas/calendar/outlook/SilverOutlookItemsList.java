/**
 * Copyright (C) 2000 - 2012 Silverpeas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * As a special exception to the terms and conditions of version 3.0 of
 * the GPL, you may redistribute this Program in connection with Free/Libre
 * Open Source Software ("FLOSS") applications as described in Silverpeas's
 * FLOSS exception.  You should have received a copy of the text describing
 * the FLOSS exception, and it is also available here:
 * "http://www.silverpeas.org/legal/licensing"
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.silverpeas.calendar.outlook;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * represents a Outlook item list
 * @author Ludovic Bertin, Xavier Delorme
 */
abstract public class SilverOutlookItemsList {

  /**
   * the activeX component
   */
  private ActiveXComponent activexOutlook;

  /**
   * Dispatch for Outlook application
   */
  private Dispatch oOutlook;

  /**
   * Dispatch for namespace
   */
  private Dispatch namespace;

  /**
   * Dispatch for Outlook folder
   */
  private Dispatch folder;

  /**
   * Dispatch for item list
   */
  private Dispatch itemsList;

  /**
   * Get the Outlook Folder Type identification See MSDN documentation
   * @return Outlook Folder Type identification
   */
  abstract protected int getOlFolder();

  /**
   * Get the Outlook Item Type identification See MSDN documentation
   * @return Outlook Item Type identification
   */
  abstract protected String getOlItem();

  /**
   * Get the Dispatch for Outlook folder
   * @return Dispatch for Outlook folder
   */
  public Dispatch getFolder() {
    return folder;
  }

  /**
   * Set the Dispatch for Outlook folder
   * @param folder Dispatch for Outlook folder
   */
  public void setFolder(Dispatch folder) {
    this.folder = folder;
  }

  /**
   * Get the Dispatch for namespace
   * @return Dispatch for namespace
   */
  public Dispatch getNamespace() {
    return namespace;
  }

  /**
   * Set the Dispatch for namespace
   * @param folder Dispatch for namespace
   */
  public void setNamespace(Dispatch namespace) {
    this.namespace = namespace;
  }

  /**
   * Get the ActiveX Component for Outlook
   * @return ActiveX Component for Outlook
   */
  public ActiveXComponent getActivexOutlook() {
    return activexOutlook;
  }

  /**
   * Set the ActiveX Component for Outlook
   * @param ax ActiveX Component for Outlook
   */
  public void setActivexOutlook(ActiveXComponent ax) {
    this.activexOutlook = ax;
  }

  /**
   * Get the Dispatch for Outlook application
   * @return Dispatch for Outlook application
   */
  public Dispatch getOOutlook() {
    return oOutlook;
  }

  /**
   * Set the Dispatch for Outlook application
   * @param olo Dispatch for Outlook application
   */
  public void setOOutlook(Dispatch olo) {
    this.oOutlook = olo;
  }

  /**
   * default constructor
   */
  public SilverOutlookItemsList() {
  }

  /**
   * Init activeX component to access Outlook
   */
  public void initWithCurrentOutlook() {
    // Outlook application
    this.setActivexOutlook(new ActiveXComponent("Outlook.Application"));
    this.setOOutlook(getActivexOutlook().getObject());
    this.setNamespace(Dispatch.call(getOOutlook(), "GetNamespace", "MAPI")
        .toDispatch());
    this.setFolder(Dispatch.call(getNamespace(), "GetDefaultFolder",
        new Integer(getOlFolder())).toDispatch());
  }

  /**
   * Retrieve items list from Outlook
   */
  public void loadItemsList(int nbDaysBefore) {
    Dispatch allItems = Dispatch.get(getFolder(), "Items").toDispatch();
    String filter = createFilter(nbDaysBefore);

    this.setItemsList(Dispatch.call(allItems, "Restrict", filter).toDispatch());
  }

  /**
   * Create a filter to retrieve
   * @return
   */
  private String createFilter(int nbDays) {
    Date today = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(today);
    cal.add(Calendar.DAY_OF_YEAR, -nbDays);
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy 00:00 a");
    return "[Start] >= '" + format.format(cal.getTime()) + "'";
  }

  /**
   * Create a new Outlook item
   * @return Dispatch representing Outlook item
   */
  protected Dispatch createNewItem() {
    return Dispatch.invoke(getOOutlook(), "CreateItem", Dispatch.Get,
        new Object[] { getOlItem() }, new int[1]).toDispatch();
  }

  /**
   * Get item list
   * @return Dispatch representing item list
   */
  public Dispatch getItemsList() {
    return itemsList;
  }

  /**
   * Set item list param list Dispatch representing item list
   */
  public void setItemsList(Dispatch list) {
    this.itemsList = list;
  }

  /**
   * Get number of items in list
   * @return number of items in list
   */
  public int getItemsListCount() {
    return Dispatch.call(getItemsList(), "Count").getInt();
  }
}
