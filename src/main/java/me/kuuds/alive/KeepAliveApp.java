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
package me.kuuds.alive;

import me.kuuds.alive.option.AppOptions;
import me.kuuds.alive.task.KeyboardTask;
import me.kuuds.alive.ui.TrayBuilder;
import me.kuuds.alive.option.AppOptions.Options;

import java.util.Timer;

/**
 * @author kuuds
 * @since 0.0.1
 */
public class KeepAliveApp {

    private static AppOptions appOptions;

    public static void main(String[] args) {
        initOptions();

        TrayBuilder.build();

        int x = Integer.valueOf(appOptions.get(Options.MOVE_X));
        int y = Integer.valueOf(appOptions.get(Options.MOVE_Y));
        long loopPeriod = Long.parseLong(appOptions.get(Options.UPDATE_PERIOD));

        Timer timer = new Timer();
        //timer.schedule(new MouseTask(x, y), 1000L, loopPeriod * 1000  * 6);
        timer.schedule(new KeyboardTask(), 1000L, loopPeriod * 1000);
    }

    private static void initOptions(){
        appOptions = new AppOptions();
//        appOptions.set(AppOptions.Options.MOVE_X, "1");
//        appOptions.set(AppOptions.Options.MOVE_Y, "1");
//        appOptions.set(AppOptions.Options.UPDATE_PERIOD, "1");
    }
}
