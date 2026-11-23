package org.crudfeed.cli.command.impl.mod;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.AccountHandler;
import org.crudfeed.cli.session.Session;
import org.crudfeed.core.entity.Role;

import static java.lang.IO.println;

public class ModBanCommand implements Command {

    private final AccountHandler accountHandler = AccountHandler.getINSTANCE();

    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public Role getRequiredRole() {
        return Role.MODERATOR;
    }

    @Override
    public boolean requiresAuth() {
        return true;
    }

    @Override
    public void execute(String argument) {
        if (argument.isEmpty()) {
            println("Usage - ban [id] ");
            return;
        }

        accountHandler.modBanAccount(Session.getINSTANCE().getCurrentUser().getId(), Long.parseLong(argument));
    }
}