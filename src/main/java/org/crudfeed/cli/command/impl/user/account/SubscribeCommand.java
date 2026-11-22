package org.crudfeed.cli.command.impl.user.account;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.AccountHandler;

import static java.lang.IO.println;

public class SubscribeCommand implements Command {

    private final AccountHandler accountHandler = AccountHandler.getINSTANCE();

    @Override
    public String getName() {
        return "subscribe";
    }

    @Override
    public boolean requiresAuth() {
        return true;
    }

    @Override
    public void execute(String argument) {
        String input = argument.trim();

        if (input.isEmpty()) {
            println("Usage - subscribe [name]");
            return;
        }

        accountHandler.subscribe(input);
    }
}