package me.kuuds.alive.task;

import java.awt.*;
import java.util.Random;
import java.util.TimerTask;

/**
 * @author kuuds
 * @since 0.0.1
 */
public class MouseTask extends TimerTask {

    private Robot robot;
    private int movePixelX;
    private int movePixelY;
    private Random random;

    private int latestX;
    private int latestY;

    private Point point = null;

    public MouseTask(int movePixelX, int movePixelY) {
        this.movePixelX = movePixelX;
        this.movePixelY = movePixelY;
        random = new Random();
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            System.err.println("Error: Can't get mouse.");
            System.exit(-1);
        }
    }

    @Override
    public void run() {
        keepAlive();
    }

    private void keepAlive() {
        if (!isMoved()) {
            move(movePixelX, movePixelY);
        }
        updateLatestPosition();
    }

    private boolean isMoved() {
        Point current = MouseInfo.getPointerInfo().getLocation();
        System.out.println("Check if moved. current: " + current.x + ", " + current.y + ". last: " + latestX + ',' + latestY);
        return latestX != current.x || latestY != current.y;
    }

    private void move(int x, int y) {
        int randX = random.nextInt(2) == 1 ? 1 : -1;
        int randY = random.nextInt(2) == 1 ? 1 : -1;
        System.out.println("MOVE!! FROM: " + point.x + ", " + point.y);
        robot.waitForIdle();
        robot.mouseMove(point.x + x * randX, point.y + y * randY);
        robot.waitForIdle();
        robot.mouseMove(point.x, point.y);
        point = MouseInfo.getPointerInfo().getLocation();
        System.out.println("MOVE!! TO: " + point.x + ", " + point.y);
        updateLatestPosition();
    }

    private void updateLatestPosition() {
        point = MouseInfo.getPointerInfo().getLocation();
        System.out.println("UPDATE!! " + point.x + "," + point.y);
        latestX = point.x;
        latestY = point.y;
    }

}