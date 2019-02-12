package me.kuuds.alive;

import me.kuuds.alive.task.MouseTask;
import me.kuuds.alive.ui.TrayBuilder;

import java.util.Timer;

/**
 * @author kuuds
 * @since 0.0.1
 */

public class KeepAliveApp {

    public static void main(String[] args) {

        int x = Integer.valueOf(System.getProperty("moveX","1"));
        int y = Integer.valueOf(System.getProperty("moveY","1"));
        long loopPeriod = Long.parseLong(System.getProperty("loopPeriod", "10"));
        TrayBuilder.build();

        Timer timer = new Timer();
        timer.schedule(new MouseTask(x, y), 1000L, loopPeriod * 1000  * 60);
    }
}
