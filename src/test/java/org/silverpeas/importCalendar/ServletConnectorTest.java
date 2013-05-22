/*
 * Copyright (C) 2000 - 2013 Silverpeas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * As a special exception to the terms and conditions of version 3.0 of
 * the GPL, you may redistribute this Program in connection withWriter Free/Libre
 * Open Source Software ("FLOSS") applications as described in Silverpeas's
 * FLOSS exception.  You should have recieved a copy of the text describing
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
package org.silverpeas.importCalendar;

import org.silverpeas.calendar.ServletConnector;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.silverpeas.calendar.api.CalendarEntry;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 *
 * @author ehugonnet
 */
public class ServletConnectorTest {

  public ServletConnectorTest() {
  }

  /**
   * Test of write method, of class ServletConnector.
   */
  @Test
  public void testWrite() throws Exception {
    List<CalendarEntry> headers = new ArrayList<CalendarEntry>(2);
    CalendarEntry simpleEntry = new CalendarEntry("test", "delegatorId");
    simpleEntry.setClassification("public");
    simpleEntry.setPriority(5);
    simpleEntry.setDescription("This is a test");
    Calendar startDay = Calendar.getInstance();
    startDay.set(Calendar.DAY_OF_MONTH, 17);
    startDay.set(Calendar.MONTH, Calendar.FEBRUARY);
    startDay.set(Calendar.YEAR, 1974);
    startDay.set(Calendar.HOUR_OF_DAY, 10);
    startDay.set(Calendar.MINUTE, 05);
    startDay.set(Calendar.SECOND, 0);
    startDay.set(Calendar.MILLISECOND, 0);
    simpleEntry.setEndDay(null);
    simpleEntry.setStartDay(startDay.getTime());
    simpleEntry.setStartHour(startDay.getTime());
    startDay.set(Calendar.HOUR_OF_DAY, 12);
    startDay.set(Calendar.MINUTE, 0);
    simpleEntry.setEndHour(startDay.getTime());
    headers.add(simpleEntry);

    CalendarEntry fullDayEntry = new CalendarEntry("test", "delegatorId");
    fullDayEntry.setClassification("public");
    fullDayEntry.setPriority(5);
    fullDayEntry.setDescription("This is a test");
    fullDayEntry.setEndDay(null);
    fullDayEntry.setStartDay(startDay.getTime());
    fullDayEntry.setStartHour(null);
    headers.add(fullDayEntry);

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ServletConnector instance = new ServletConnector("http://www.google.fr");
    instance.write(headers, out);
    byte[] output = out.toByteArray();
    assertThat(output, is(notNullValue()));
    assertThat(output.length, is(447));
    assertThat(new String(output, "UTF-8"), is("[{\"id\":null,\"name\":\"test\","
        + "\"delegatorId\":\"delegatorId\",\"description\":\"This is a test\","
        + "\"classification\":\"public\",\"startHour\":\"10:05\",\"endHour\":\"12:00\","
        + "\"priority\":5,\"externalId\":null,\"endDay\":\"1974/02/17\",\"startDay\":\"1974/02/17\"},"
        + "{\"id\":null,\"name\":\"test\",\"delegatorId\":\"delegatorId\","
        + "\"description\":\"This is a test\",\"classification\":\"public\",\"startHour\":null,"
        + "\"endHour\":null,\"priority\":5,\"externalId\":null,\"endDay\":\"1974/02/17\","
        + "\"startDay\":\"1974/02/17\"}]"));
  }
}