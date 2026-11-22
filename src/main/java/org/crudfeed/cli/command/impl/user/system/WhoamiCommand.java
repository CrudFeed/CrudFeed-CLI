package org.crudfeed.cli.command.impl.user.system;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.AccountHandler;

public class WhoamiCommand implements Command {

    private final AccountHandler accountHandler = AccountHandler.getINSTANCE();

    @Override
    public String getName() {
        return "whoami";
    }

    @Override
    public void execute(String argument) { accountHandler.whoami(); }
}