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

import java.util.Date;

import org.silverpeas.calendar.api.SilverEvent;

import com.jacob.com.Dispatch;

/**
 * SilverOutlookEvent class represents an Outlook event
 *
 * @authors Ludovic Bertin, Xavier Delorme
 */
public class SilverOutlookEvent extends SilverOutlookItem implements SilverEvent {

  /**
   * Get the event subject
   *
   * @return event subject
   */
  @Override
  public String getSubject() {
    return Dispatch.get(this, "Subject").toString();
  }

  /**
   * Set the event subject
   *
   * @param subject event subject
   */
  @Override
  public void setSubject(String subject) {
    Dispatch.put(this, "Subject", subject);
  }

  /**
   * Get the event body
   *
   * @return event body
   */
  @Override
  public String getBody() {
    return Dispatch.get(this, "Body").toString();
  }

  /**
   * Set the event body
   *
   * @param body event body
   */
  @Override
  public void setBody(String body) {
    Dispatch.put(this, "Body", body);
  }

  /**
   * Get the event importance
   *
   * @return event importance
   */
  @Override
  public int getImportance() {
    String importance = Dispatch.get(this, "Importance").toString();
    if ("1".equals(importance)) {
      return 0;
    }
    if ("2".equals(importance)) {
      return 1;
    }
    if ("0".equals(importance)) {
      return 3;
    }
    return 3;
  }

  /**
   * Set the event importance
   *
   * @param importance event importance
   */
  @Override
  public void setImportance(int importance) {
    String importanceString;

    switch (importance) {
      case 0:
        importanceString = "1";
        break;
      case 1:
        importanceString = "2";
        break;
      case 3:
      default:
        importanceString = "0";
    }
    Dispatch.put(this, "Importance", importanceString);
  }

  /**
   * Get the event start date
   *
   * @return event start date
   */
  @Override
  public Date getStart() {
    return Dispatch.get(this, "Start").getJavaDate();
  }

  /**
   * Set the event start date
   *
   * @param start event start date
   */
  @Override
  public void setStart(Date start) {
    Dispatch.put(this, "Start", start);
  }

  /**
   * Get the event end date
   *
   * @return event end date
   */
  @Override
  public Date getEnd() {
    return Dispatch.get(this, "End").getJavaDate();
  }

  /**
   * Set the event end date
   *
   * @param end event end date
   */
  @Override
  public void setEnd(Date end) {
    Dispatch.put(this, "End", end);
  }

  /**
   * Get the allDayEvent property
   *
   * @return true is event is declared as allday
   */
  @Override
  public boolean getAllDayEvent() {
    return "true".equals(Dispatch.get(this, "AllDayEvent").toString());
  }

  /**
   * Set the allDayEvent property
   *
   * @param allDayEvent true to declare event as allday, false otherwise
   */
  @Override
  public void setAllDayEvent(boolean allDayEvent) {
    Dispatch.put(this, "AllDayEvent", Boolean.valueOf(allDayEvent));
  }

  /**
   * Get the event sensitivity
   *
   * @return event sensitivity
   */
  @Override
  public String getSensitivity() {
    String sensitivity = Dispatch.get(this, "Sensitivity").toString();
    if (!sensitivity.equals("0")) {
    } else {
      return "public";
    }
    return "private";
  }

  /**
   * Set the event sensitivity
   *
   * @param sensitivity event sensitivity
   */
  @Override
  public void setSensitivity(String sensitivity) {
    Dispatch.put(this, "Sensitivity", sensitivity);
  }

  /**
   * Get the event id
   *
   * @return event id
   */
  @Override
  public String getEntryId() {
    return Dispatch.get(this, "EntryId").toString();
  }

  /**
   * Set the event id
   *
   * @param entryId event id
   */
  @Override
  public void setEntryId(String entryId) {
    Dispatch.put(this, "EntryId", entryId);
  }

  /**
   * A SilverOutlookEvent is build with a given Dispatch object representing event got from Outlook
   *
   * @param disp Dispatch object retrieved by Jacob library
   */
  public SilverOutlookEvent(Dispatch disp) {
    super(disp);
  }
}
