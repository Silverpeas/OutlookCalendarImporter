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

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComFailException;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * OutlookSecurityManager allows to switch ON/OFF Outlook Security warnings In order to work,
 * "Outlook Security Manager" must be installed on user-end host. ( see
 * http://www.add-in-express.com/outlook-security/ )
 *
 * @author Ludovic Bertin
 */
public class OutlookSecurityManager {

  private ActiveXComponent mOutlookSecurityManagerDispatch;
  private Dispatch mOutlookApplication;

  /**
   * OutlookSecurityManager is instanciating using a Dispatch object that represents Outlook
   * application
   *
   * @param outlook Dispatch object representing Outlook application
   */
  public OutlookSecurityManager(Dispatch outlook) {
    mOutlookSecurityManagerDispatch = new ActiveXComponent("secman.OutlookSecurityManager");
    mOutlookApplication = outlook;
    mOutlookSecurityManagerDispatch.invoke("Connect",
        new Variant[]{new Variant(mOutlookApplication)});
    int checkValue = mOutlookSecurityManagerDispatch.invoke("Check", new Variant[]{new Variant(3)})
        .getInt();
    if (checkValue != 0) {
      throw new ComFailException("Unable to initialize the Outlook Security Manager (" + checkValue
          + ")");
    }
  }

  /**
   * Switch ON/OFF Outlook security warnings
   *
   * @param enabled false to disable warnings
   */
  public void setSecurityWarningsEnabled(boolean enabled) {
    mOutlookSecurityManagerDispatch.invoke("Switch", new Variant[]{new Variant(3), new Variant(
      enabled ? 0 : 1)});
  }

}
