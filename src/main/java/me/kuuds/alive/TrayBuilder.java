package me.kuuds.alive;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class TrayBuilder {

    private PopupMenu trayMenu;
    private TrayIcon trayIcon;

    public TrayBuilder() {
        trayIcon = buildTrayIcon();
        trayMenu = buildTrayMenu();
        trayIcon.setPopupMenu(trayMenu);
        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
            System.err.println("can't create trayIcon");
            System.exit(-1);
        }
    }

    private TrayIcon buildTrayIcon(){
        URL url = this.getClass().getResource("/images/tray_icon.png");

        ImageIcon icon = new ImageIcon(url);
        Image image = icon.getImage();
        return new TrayIcon(image);
    }

    private PopupMenu buildTrayMenu(){
        PopupMenu popupMenu = new PopupMenu();
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        popupMenu.add(exitMenuItem);
        return popupMenu;
    }
}
