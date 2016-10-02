package com.evgenyshilov.web.contacts.help;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Evgeny Shilov on 02.10.2016.
 */
public class LogHelper {
    private static final Logger logger = LoggerFactory.getLogger(LogHelper.class);

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message, Throwable t) {
        logger.error(message, t);
    }
}
