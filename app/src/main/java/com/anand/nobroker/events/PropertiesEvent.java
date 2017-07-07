package com.anand.nobroker.events;


import com.anand.nobroker.model.pojo.Properties;

public class PropertiesEvent {

    private Properties properties;

    public PropertiesEvent(Properties properties) {
        this.properties = properties;
    }

    public Properties getProperteies() {
        return properties;
    }
}
