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
package org.silverpeas.calendar.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarEntriesFactory {

  private final String sessionId;

  public CalendarEntriesFactory(String sessionId) {
    this.sessionId = sessionId;
  }

  public List<CalendarEntry> buildJournalHeadersList(SilverEventsList myEvents) {
    List<CalendarEntry> headers = new ArrayList<CalendarEntry>(myEvents.getItemsListCount());
    for (int i = 1; i <= myEvents.getItemsListCount(); i++) {
      headers.add(createJournalHeader(myEvents.getSilverEvent(i)));
    }
    return headers;
  }

  public CalendarEntry createJournalHeader(SilverEvent event) {
    CalendarEntry journal = new CalendarEntry(event.getSubject(), sessionId);
    journal.setDescription(event.getBody());
    journal.setPriority(event.getImportance());
    journal.setClassification(event.getSensitivity());
    journal.setStartDay(event.getStart());
    journal.setEndDay(event.getEnd());

    if (event.getAllDayEvent()) {
      journal.setStartHour(null);
      journal.setEndHour(null);
      Calendar cal = Calendar.getInstance();
      cal.setTime(event.getEnd());
      cal.add(Calendar.DAY_OF_YEAR, -1);
      journal.setEndDay(cal.getTime());
    } else {
      journal.setStartHour(event.getStart());
      journal.setEndHour(event.getEnd());
    }

    journal.setExternalId(event.getEntryId());
    return journal;
  }
}
