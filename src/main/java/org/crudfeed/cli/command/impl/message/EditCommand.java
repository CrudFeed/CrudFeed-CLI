package org.crudfeed.cli.command.impl.message;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.MessageHandler;

import static java.lang.IO.*;


public class EditCommand implements Command {

    private final MessageHandler messageHandler = MessageHandler.getINSTANCE();

    @Override
    public String getName() {
        return "edit";
    }

    @Override
    public void execute(String argument) {
        String input = argument.trim();

        if (input.isEmpty()) {println("Usage - edit [id] [text]"); return;}

        String[] credentials = input.split("\\s+", 2);

        if (credentials.length < 2) {
            println("Missing argument. Usage - edit [id] [text]");
        }

        String messageId = credentials[0];
        String content = credentials[1];

        messageHandler.editMessage(Long.parseLong(messageId), content);


    }
}