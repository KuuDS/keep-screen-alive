package me.kuuds.alive;

import java.awt.*;
import java.util.Random;
import java.util.TimerTask;

/**
 * @author lkf7700
 */
@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
public class MouseController extends TimerTask {

    private Robot robot;
    private int movePixelX;
    private int movePixelY;
    private Random random;

    private int latestX;
    private int latestY;

    private Point point = null;

    private Thread updatePointInfoThread;

    MouseController(int movePixelX, int movePixelY, final long updatePeriod) {
        this.movePixelX = movePixelX;
        this.movePixelY = movePixelY;
        random = new Random();
        //noinspection AlibabaAvoidManuallyCreateThread
        updatePointInfoThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    updateLatestPosition();
                    try {
                        System.out.println("UPDATE SLEEP");
                        Thread.sleep(updatePeriod / 2);
                        System.out.println("UPDATE WAKEUP");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        updatePointInfoThread.setName("POSITION-UPDATE");

    }


    @Override
    public void run() {
        keepAlive();
    }

    void init() {
        try {
            robot = new Robot();
            updatePointInfoThread.start();
        } catch (AWTException e) {
            System.out.println("Error: Can't get mouse.");
            System.exit(-1);
        }
    }

    private void keepAlive() {
        if (!isMoved()) {
            move(movePixelX, movePixelY);
        }

    }

    private boolean isMoved() {
        Point current = MouseInfo.getPointerInfo().getLocation();
        return point != null && latestX != current.x || latestY != current.y;
    }

    private void move(int x, int y) {
        int randX = random.nextInt(100) % 2 == 0 ? 1 : -1;
        int randY = random.nextInt(100) % 2 == 0 ? 1 : -1;
        System.out.println("MOVE!! FROM: " + point.x + ", " + point.y);
        robot.waitForIdle();
        robot.mouseMove(point.x + x * randX, point.y + y * randY);
        point = MouseInfo.getPointerInfo().getLocation();
        System.out.println("MOVE!! TO: " + point.x + ", " + point.y);

    }

    private void updateLatestPosition() {

        point = MouseInfo.getPointerInfo().getLocation();
        System.out.println("UPDATE!! " + point.x + "," + point.y);
        latestX = point.x;
        latestY = point.y;
    }

}
