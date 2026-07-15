package org.crudfeed.cli.command.impl.system;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.AccountHandler;

public class StatusCommand implements Command {

    private final AccountHandler accountHandler = AccountHandler.getINSTANCE();

    @Override
    public String getName() {
        return "status";
    }

    @Override
    public void execute(String argument) { accountHandler.status(); }
}