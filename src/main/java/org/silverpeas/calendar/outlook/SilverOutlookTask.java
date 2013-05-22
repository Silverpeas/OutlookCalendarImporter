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

import com.jacob.com.Dispatch;

public class SilverOutlookTask extends SilverOutlookItem {
  public SilverOutlookTask(Dispatch disp) {
    super(disp);
  }

  public String getSubject() {
    return Dispatch.get(this, "Subject").toString();
  }

  public void setSubject(String subject) {
    Dispatch.put(this, "Subject", subject);
  }

  public void setDescription(String desc) {
    Dispatch.put(this, "Body", desc);
  }

  public String getDescription() {
    return Dispatch.get(this, "Body").toString();
  }

  public void setStatus(String status) {
    Dispatch.put(this, "Status", status);
  }

  public String getStatus() {
    return Dispatch.get(this, "Status").toString();
  }

  public String getStart() {
    return Dispatch.get(this, "Startdate").toString();
  }

  public void setStart(String start) {
    Dispatch.put(this, "Startdate", start);
  }

  public String getDueDate() {
    return Dispatch.get(this, "Duedate").toString();
  }

  public void setDueDate(String due) {
    Dispatch.put(this, "Duedate", due);
  }
}
