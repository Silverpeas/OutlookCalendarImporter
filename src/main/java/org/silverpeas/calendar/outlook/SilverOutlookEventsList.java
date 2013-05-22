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

import org.silverpeas.calendar.api.SilverEvent;
import org.silverpeas.calendar.api.SilverEventsList;

import com.jacob.com.Dispatch;

/**
 * SilverOutlookEventsList represents a list of Outlook events
 *
 * @authors Ludovic Bertin, Xavier Delorme
 */
public class SilverOutlookEventsList extends SilverOutlookItemsList implements SilverEventsList {

  /**
   * Type identification for Outlook Folder Calendar
   */
  private final static int olFolderCalendar = 9;

  /**
   * Type identification for Outlook Calendar Item
   */
  private final static String olItem = "1"; // cf OlItemType

  /**
   * Default constructor
   */
  public SilverOutlookEventsList() {
    super();
  }

  /**
   * Get Outlook Silverpeas event at given position in the list
   *
   * @param i position
   * @return SilverOutlookEvent object
   */
  @Override
  public SilverEvent getSilverEvent(int i) {
    Dispatch disp = Dispatch.call(getItemsList(), "Item", Integer.valueOf(i)).toDispatch();
    return new SilverOutlookEvent(disp);
  }

  /**
   * Get the Outlook Folder Type identification
   *
   * @return
   */
  @Override
  protected int getOlFolder() {
    return olFolderCalendar;
  }

  /**
   * Get the Outlook Item Type identification
   *
   * @return
   */
  @Override
  protected String getOlItem() {
    return olItem;
  }
}
