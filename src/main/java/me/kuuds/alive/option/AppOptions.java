package me.kuuds.alive.option;

import java.util.EnumMap;
import java.util.Properties;

public final class AppOptions {

    public enum Options {
        MOVE_X,
        MOVE_Y,
        UPDATE_PERIOD
    }

    private final EnumMap<Options, String> optionMap;

    public AppOptions() {
        this(null);
    }

    public AppOptions(Properties properties) {
        optionMap = new EnumMap<>(Options.class);
        if (properties != null) {
            initialize(properties);
        }
    }

    private void initialize(Properties properties) {

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
