package org.crudfeed.cli.command.impl.message;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.ui.MessageCardCreator;
import org.crudfeed.core.entity.Session;
import org.crudfeed.cli.handler.MessageHandler;

import static java.lang.IO.*;

public class ShowCommand implements Command {

    private final MessageHandler messageHandler = MessageHandler.getINSTANCE();
    private final MessageCardCreator messageCardCreator = MessageCardCreator.getINSTANCE();
    private final Session session = Session.getINSTANCE();

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public void execute(String argument) {
        String input = argument.trim();

        if (input.isEmpty()) {
            println("Usage - show [id]");
            return;
        }

        println(messageCardCreator.createMessageCard(
                messageHandler.viewMessages(argument),
                session.getCurrentUser().getId())
        );
    }
}