package org.crudfeed.cli.command.impl.message;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.MessageHandler;

public class DeleteCommand implements Command {

    private final MessageHandler messageHandler = MessageHandler.getINSTANCE();

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public void execute(String argument) { messageHandler.deleteMessage(Long.parseLong(argument)); }
}