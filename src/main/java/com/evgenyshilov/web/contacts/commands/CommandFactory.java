package com.evgenyshilov.web.contacts.commands;

import com.evgenyshilov.web.contacts.utils.PropertyFileParser;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Evgeny Shilov on 11.09.2016.
 */
public class CommandFactory {

    private static HashMap<String, Class<? extends Command>> commands = new HashMap<>();

    public void init(String filePath) throws IOException, ClassNotFoundException {
        HashMap<String, String> stringPairs = new PropertyFileParser().parse(filePath);
        for (String key : stringPairs.keySet()) {
            String commandClassName = stringPairs.get(key);
            Class<? extends Command> commandClass = Class.forName(commandClassName).asSubclass(Command.class);
            commands.put(key, commandClass);
        }
    }

    public Command create(String URI) throws IllegalAccessException, InstantiationException {
        Class<? extends Command> commandClass = commands.get(URI);
        return commandClass != null ? commandClass.newInstance() : null;
    }

}
