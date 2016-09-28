package com.evgenyshilov.web.contacts.resources;

import java.util.Properties;

/**
 * Created by Evgeny Shilov on 28.09.2016.
 */
public class ApplicationConfig {
    private static Properties properties;

    public static void initConfig(Properties properties) {
        ApplicationConfig.properties = properties;
    }

    public static String getProperty(String key) {
        return properties != null ? properties.getProperty(key) : null;
    }
}
