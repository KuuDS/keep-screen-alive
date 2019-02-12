package me.kuuds.alive;

import java.awt.*;
import java.util.Timer;

/**
 * @author kuuds
 * @since 0.0.1
 */

public class KeepAliveApp {

    public static void main(String[] args) {

        int x = Integer.valueOf(System.getProperty("moveX","1"));
        int y = Integer.valueOf(System.getProperty("moveY","1"));
        long loopPeriod = Long.parseLong(System.getProperty("loopPeriod", "5"));

        if(SystemTray.isSupported()){
            new TrayBuilder();
        }

        MouseController mouseController = new MouseController(x, y, loopPeriod * 60 * 1000);
        mouseController.init();

        Timer timer = new Timer();
        timer.schedule(mouseController, 1000L, loopPeriod);
    }
}
