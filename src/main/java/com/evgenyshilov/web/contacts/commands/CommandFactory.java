package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.exceptions.CustomException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Evgeny Shilov on 11.09.2016.
 */
public class CommandFactory {

    private static HashMap<String, Class<? extends Command>> commands = new HashMap<>();

    public void init(String filePath) throws CustomException {
        Properties mappingProperties = new Properties();
        FileInputStream propertiesInputStream;
        try {
            propertiesInputStream = new FileInputStream(filePath);
            mappingProperties.load(propertiesInputStream);
            for (String key : mappingProperties.stringPropertyNames()) {
                String commandClassName = mappingProperties.getProperty(key);
                try {
                    Class<? extends Command> commandClass = Class.forName(commandClassName).asSubclass(Command.class);
                    commands.put(key, commandClass);
                } catch (ClassNotFoundException e) {
                    // TODO log exception
                }
            }
        } catch (FileNotFoundException e) {
            throw new CustomException("Command mapping file was not found: ", e);
        } catch (IOException e) {
            throw new CustomException("Cannot load properties from file: ", e);
        }
    }

    public Command create(String URI) throws IllegalAccessException, InstantiationException {
        Class<? extends Command> commandClass = commands.get(URI);
        return commandClass != null ? commandClass.newInstance() : null;
    }

}
