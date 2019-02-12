package me.kuuds.alive.option;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class AppOptions {

    final Map<String, String> optionMap;

    public AppOptions(Properties properties) {
        optionMap = new HashMap<>();
        initialize(properties);
    }

    private void initialize(Properties properties){

    }

    public String getOption(String properties){
        return optionMap.get(properties);
    }

    public String getOption(String properties, String defaultValue){
        return optionMap.getOrDefault(properties, defaultValue);
    }

    void saveProperties(){

    }
}
