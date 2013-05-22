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

package org.silverpeas.calendar;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 * AppletDrawer is an helper for the importation applets. It provides methods to manage graphical
 * functionalities
 * @authors Ludovic Bertin, Xavier Delorme
 */
public class AppletDrawer {
  protected static final int TIMEOUT = 30000;

  private static final int SCROLLING_TIMER = 30;
  private static final int DISPLAY_DURATION = 5000;

  private static final int APPLET_HEIGHT = 150;
  private static final int APPLET_WIDTH = 350;

  private static final int CADRE_HEIGHT = 150;
  private static final int CADRE_WIDTH = 300;

  private JWindow cadre;
  private int baseUpperLeftCornerXPosition;
  private int baseUpperLeftCornerYPosition;

  private JPanel panneau;
  private TextArea messageList;
  private StringBuffer messages;

  /**
   * default constructor Initializes graphic components
   */
  public AppletDrawer() {
    messages = new StringBuffer("");
    panneau = new JPanel();
    cadre = new JWindow();

    messageList = addTextArea(messages.toString());
    panneau.add("Center", messageList);

    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int _screenWidth = screen.width;
    int _screenHeight = screen.height;

    baseUpperLeftCornerXPosition = _screenWidth + 16 - CADRE_WIDTH;
    baseUpperLeftCornerYPosition = _screenHeight + 16;

    cadre.getContentPane().add("Center", panneau);
    cadre.setSize(CADRE_WIDTH, CADRE_HEIGHT);
    cadre.setLocation(_screenWidth - CADRE_WIDTH, _screenHeight);
    cadre.setVisible(true);

  }

  /**
   * Initializes applet size and message
   * @param applet applet to initialize
   * @param msg message to display
   */
  public void initApplet(Applet applet, String msg) {
    applet.setSize(APPLET_WIDTH, APPLET_HEIGHT);
    clearMessage();
    addMessage(msg);
  }

  /**
   * Show bottom-left gliding area
   */
  public final void showDialog() {
    for (int i = 10; i <= CADRE_HEIGHT; i += 10) {
      cadre.setLocation(baseUpperLeftCornerXPosition, baseUpperLeftCornerYPosition - i);
      pause(SCROLLING_TIMER);
    }

    pause(DISPLAY_DURATION);

    for (int i = CADRE_HEIGHT; i >= 0; i -= 10) {
      cadre.setLocation(baseUpperLeftCornerXPosition, baseUpperLeftCornerYPosition - i);
      pause(SCROLLING_TIMER);
    }

  }

  /**
   * Clear message
   */
  public void clearMessage() {
    messages = new StringBuffer();
    messageList.setText(messages.toString());
    cadre.setVisible(true);
  }

  /**
   * Add a message to message list
   * @param message message to add
   */
  public void addMessage(String message) {
    messages.append(message);
    messageList.setText(messages.toString());
    cadre.setVisible(true);
  }

  /**
   * Pause current thread with defaut timeout (30s)
   */
  public void pause() {
    pause(TIMEOUT);
  }

  /**
   * Pause current thread with given delay (in milliseconds)
   * @param milliseconds delay in milliseconds
   */
  protected final void pause(int milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException interruptedexception) {
      // on ignore le probleme pour l'instant
      interruptedexception.printStackTrace();
    }
  }

  /**
   * Create a TextArea object with given text
   * @param text text to include in Text Area
   * @return a Text Area object with given text
   */
  protected TextArea addTextArea(String text) {
    TextArea textArea = new TextArea(text, 8, 40);
    textArea.setBounds(0, 0, 400, 200);
    textArea.setBackground(Color.BLUE);
    textArea.setForeground(Color.WHITE);
    return textArea;
  }

}
