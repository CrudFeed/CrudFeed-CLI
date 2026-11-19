package org.crudfeed.cli.command.impl.message;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.MessageHandler;

import static java.lang.IO.*;

public class WriteCommand implements Command {

    private final MessageHandler messageHandler = MessageHandler.getINSTANCE();

    @Override
    public String getName() {
        return "write";
    }

    @Override
    public void execute(String argument) {
        String input = argument.trim();

        if (input.isEmpty()) {
            println("Usage - write [name] [text]");
            return;
        }

        String[] credentials = input.split("\\s+", 2);

        if (credentials.length < 2) {
            println("Missing argument. Usage - write [name] [text]");
            return;
        }

        String receiverId = credentials[0];
        String content = credentials[1];

        messageHandler.writeMessage(Long.parseLong(receiverId), content);

    }
}