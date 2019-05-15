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
package me.kuuds.alive.option;

import java.util.EnumMap;
import java.util.Properties;

/**
 * @author kuuds
 * @since 0.0.1
 */
public final class AppOptions {

    public enum Options {
        MOVE_X,
        MOVE_Y,
        UPDATE_PERIOD
    }

    private final EnumMap<Options, String> optionMap;

    public AppOptions() {
        this(System.getProperties());
    }

    public AppOptions(Properties properties) {
        optionMap = new EnumMap<>(Options.class);
        if (properties != null) {
            initialize(properties);
        }
    }

    private void initialize(Properties properties) {
        optionMap.put(Options.MOVE_X, properties.getProperty("moveX", "1"));
        optionMap.put(Options.MOVE_Y, properties.getProperty("moveY", "1"));
        optionMap.put(Options.UPDATE_PERIOD, properties.getProperty("loopPeriod", "10"));

    }

    public String get(Options key) {
        return optionMap.get(key);
    }

    public void saveProperties() {

    }

    public String set(Options key, String value) {
        return optionMap.put(key, value);
    }
}
