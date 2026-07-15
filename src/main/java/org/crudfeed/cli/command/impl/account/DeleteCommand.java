package org.crudfeed.cli.command.impl.account;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.AccountHandler;

public class DeleteCommand implements Command {

    private final AccountHandler accountHandler = AccountHandler.getINSTANCE();

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public void execute(String argument) {
        accountHandler.deleteAccount();
    }
}