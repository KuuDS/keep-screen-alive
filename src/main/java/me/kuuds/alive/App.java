package me.kuuds.alive;

import java.util.Timer;

public class App {

    public static void main(String[] args) {

        Integer x = Integer.valueOf(System.getProperty("moveX","1"));
        Integer y = Integer.valueOf(System.getProperty("moveY","1"));
        Long loopPeriod = Long.valueOf(System.getProperty("loopPeriod","5"));



        MouseController mouseController = new MouseController(x, y, loopPeriod * 60 * 1000);
        mouseController.init();

        @SuppressWarnings("AlibabaAvoidUseTimer")
        Timer timer = new Timer();
        timer.schedule(mouseController, 1000L, loopPeriod);
    }
}
