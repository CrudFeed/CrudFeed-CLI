package org.crudfeed.cli.command.impl.account;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.AccountHandler;

import static java.lang.IO.println;

public class UnSubscribeCommand implements Command {

    private final AccountHandler accountHandler = AccountHandler.getINSTANCE();

    @Override
    public String getName() {
        return "unsubscribe";
    }

    @Override
    public void execute(String argument) {
        String input = argument.trim();

        if (input.isEmpty()) {
            println("Usage - unsubscribe [name]");
            return;
        }

        accountHandler.unSubscribe(input);
    }
}