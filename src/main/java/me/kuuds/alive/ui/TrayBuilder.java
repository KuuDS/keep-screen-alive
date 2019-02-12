package me.kuuds.alive.ui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class TrayBuilder {

    private Dimension dimension;
    private volatile static TrayBuilder builder;

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
            System.out.println("icon size: " + dimension.height + " px, " + dimension.width + " px.");

            TrayIcon trayIcon = buildTrayIcon();
            PopupMenu trayMenu = buildTrayMenu();
            trayIcon.setPopupMenu(trayMenu);
            systemTray.add(trayIcon);
        }
    }

    private TrayIcon buildTrayIcon() {
        URL url = this.getClass().getResource("/images/tray_icon.png");
        ImageIcon icon = new ImageIcon(url);
        Image image = icon.getImage().getScaledInstance(dimension.width, dimension.height, 10);
        return new TrayIcon(image);
    }

    private PopupMenu buildTrayMenu() {
        PopupMenu popupMenu = new PopupMenu();

        popupMenu.insert(titleMenuItem(), 0);
        popupMenu.insertSeparator(1);
        popupMenu.insert(exitMenuItem(), 2);
        return popupMenu;
    }

    private MenuItem titleMenuItem() {
        MenuItem titleMenuItem = new MenuItem("Keep Windows Alive");
        titleMenuItem.setEnabled(false);
        return titleMenuItem;
    }

    private MenuItem exitMenuItem() {
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        return exitMenuItem;
    }
}
