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
package me.kuuds.alive.task;

import java.awt.*;
import java.util.Random;
import java.util.TimerTask;

/**
 * @author kuuds
 * @since 0.0.1
 */
@Deprecated
public class MouseTask extends TimerTask {

    private Robot robot;
    private int movePixelX;
    private int movePixelY;
    private Random random;

    private int latestX;
    private int latestY;

    private Point current = null;

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
            try {
                move(movePixelX, movePixelY);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("thread interrupted while move.");
            }
        }
        updateLatestPosition();
    }

    private boolean isMoved() {
        current = MouseInfo.getPointerInfo().getLocation();
        System.out.println("Check if moved. current: " + current.x + ", " + current.y + ". last: " + latestX + ',' + latestY);
        return latestX != current.x || latestY != current.y;
    }

    private void move(int x, int y) throws InterruptedException {
        int randX = random.nextInt(2) == 1 ? 1 : -1;
        int randY = random.nextInt(2) == 1 ? 1 : -1;
        System.out.println("MOVE!! FROM: " + current.x + ", " + current.y);
        robot.waitForIdle();
        robot.mouseMove(current.x + x * randX, current.y + y * randY);
        printCurrentPointLocation();
    }

    private void updateLatestPosition() {
        current = MouseInfo.getPointerInfo().getLocation();
        System.out.println("Update Location " + current.x + "," + current.y);
        latestX = current.x;
        latestY = current.y;
    }

    private void printCurrentPointLocation() {
        current = MouseInfo.getPointerInfo().getLocation();
        System.out.println("Current Location: " + current.x + ", " + current.y);
    }

}
