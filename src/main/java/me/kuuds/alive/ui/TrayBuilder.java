/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.kuuds.alive.ui;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import javax.swing.*;
import java.awt.*;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.time.Duration;
import java.time.Period;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author kuuds
 * @since 0.0.1
 */
@Slf4j
public class TrayBuilder {

  private Dimension dimension;
  private volatile static TrayBuilder builder;
  private PeriodFormatter formatter = new PeriodFormatterBuilder()
    .appendDays()
    .appendSuffix("d")
    .appendHours()
    .appendSuffix("H")
    .appendMinutes()
    .appendSuffix("m")
    .appendSeconds()
    .appendSuffix("s")
    .toFormatter();

  public static void build() {
    if (builder == null) {
      try {
        builder = new TrayBuilder();
      } catch (AWTException e) {
        e.printStackTrace();
        System.err.println("can't create trayIcon");
        System.exit(-1);
      }
    }
  }

  private TrayBuilder() throws AWTException {
    if (SystemTray.isSupported()) {
      SystemTray systemTray = SystemTray.getSystemTray();
      dimension = systemTray.getTrayIconSize();
      log.debug("icon size: {} px, {} px.", dimension.width, dimension.height);


      TrayIcon trayIcon = buildTrayIcon();
      PopupMenu trayMenu = buildTrayMenu();
      trayIcon.setPopupMenu(trayMenu);
      systemTray.add(trayIcon);
    }
  }

  private TrayIcon buildTrayIcon() {
    URL url = this.getClass().getResource("/images/tray_icon.png");
    ImageIcon icon = new ImageIcon(url);
    Image image = icon.getImage().getScaledInstance(dimension.width, dimension.height, Image.SCALE_SMOOTH);
    return new TrayIcon(image);
  }

  private PopupMenu buildTrayMenu() {
    PopupMenu popupMenu = new PopupMenu();
    popupMenu.insert(titleMenuItem(), 0);
    popupMenu.insertSeparator(1);
    popupMenu.insert(statsMenuItem(), 2);
    popupMenu.insert(exitMenuItem(), 3);
    return popupMenu;
  }

  private MenuItem titleMenuItem() {
    MenuItem titleMenuItem = new MenuItem("Keep Windows Alive");
    titleMenuItem.setFont(new Font("sans-serif", Font.BOLD, 12));
    titleMenuItem.setEnabled(true);
    return titleMenuItem;
  }

  private MenuItem statsMenuItem() {
    MenuItem statsMenuItem = new MenuItem();
    statsMenuItem.setFont(new Font("sans-serif", Font.TRUETYPE_FONT, 12));
    statsMenuItem.setEnabled(true);

    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        statsMenuItem.setLabel(String.format("UPTIME: %s", uptimeString()));
      }
    }, 1000, 1000);
    return statsMenuItem;
  }

  private MenuItem exitMenuItem() {
    MenuItem exitMenuItem = new MenuItem("Exit");
    exitMenuItem.addActionListener(e -> System.exit(0));
    return exitMenuItem;
  }

  private String uptimeString() {
    org.joda.time.Duration uptime = new org.joda.time.Duration(ManagementFactory.getRuntimeMXBean().getUptime());
    return formatter.print(uptime.toPeriod());
  }

}
