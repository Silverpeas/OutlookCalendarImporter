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

package org.silverpeas.calendar.api;

import java.util.Date;

/**
 * This interface represents a Silverpeas event
 * @authors Xavier Delorme, Ludovic Bertin
 */
public interface SilverEvent {

  /**
   * Get the event subject
   * @return event subject
   */
  String getSubject();

  /**
   * Set the event subject
   * @param subject event subject
   */
  void setSubject(String subject);

  /**
   * Get the event body
   * @return event body
   */
  String getBody();

  /**
   * Set the event body
   * @param body event body
   */
  void setBody(String body);

  /**
   * Get the event importance
   * @return event importance
   */
  int getImportance();

  /**
   * Set the event importance
   * @param importance event importance
   */
  void setImportance(int importance);

  /**
   * Get the event start date
   * @return event start date
   */
  Date getStart();

  /**
   * Set the event start date
   * @param start event start date
   */
  void setStart(Date start);

  /**
   * Get the event end date
   * @return event end date
   */
  Date getEnd();

  /**
   * Set the event end date
   * @param end event end date
   */
  void setEnd(Date end);

  /**
   * Get the allDayEvent property
   * @return true is event is declared as allday
   */
  boolean getAllDayEvent();

  /**
   * Set the allDayEvent property
   * @param allDayEvent true to declare event as allday, false otherwise
   */
  void setAllDayEvent(boolean allDayEvent);

  /**
   * Get the event sensitivity
   * @return event sensitivity
   */
  String getSensitivity();

  /**
   * Set the event sensitivity
   * @param sensitivity event sensitivity
   */
  void setSensitivity(String sensitivity);

  /**
   * Get the event id
   * @return event id
   */
  String getEntryId();

  /**
   * Set the event id
   * @param entryId event id
   */
  void setEntryId(String entryId);

  /**
   * Save the event.
   */
  void save();
}
