package com.evgenyshilov.web.contacts.exceptions;

/**
 * Created by Evgeny Shilov on 28.09.2016.
 */
public class CustomException extends Exception {
    public CustomException(String message, Exception exception) {
        super(message, exception);
    }

    @Override
    public String toString() {
        return getMessage() + getCause();
    }
}
