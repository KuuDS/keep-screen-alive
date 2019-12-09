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
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class RobotWrapper {

    private final List<Robot> ROBOTS;
    private final Integer MAX_SIZE;
    private volatile int cur = -1;
    private static RobotWrapper instance = new RobotWrapper(1);

    public synchronized static RobotWrapper getInstance(){
        return instance;
    }

    private RobotWrapper(Integer size) {
        MAX_SIZE = size;
        ROBOTS = new ArrayList<>(MAX_SIZE);
    }

    public synchronized Robot get() throws AWTException {
        cur = cur == MAX_SIZE - 1 ? 0 : cur + 1;
        Robot instance = null;
        try {
            instance = ROBOTS.get(cur);
        } catch (Exception e){
            instance = null;
        } finally {
            if(instance == null) {
                instance = new Robot();
                ROBOTS.add(cur, instance);
            }
        }
        return instance;
    }


}
