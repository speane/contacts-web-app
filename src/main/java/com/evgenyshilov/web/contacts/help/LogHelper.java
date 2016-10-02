package com.evgenyshilov.web.contacts.help;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Evgeny Shilov on 02.10.2016.
 */
public class LogHelper {
    private static final Logger logger = LogManager.getRootLogger();

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message, Throwable t) {
        logger.error(message, t);
    }
}
