package org.crudfeed.cli.command.impl.mod;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.AccountHandler;
import org.crudfeed.core.entity.Role;

import static java.lang.IO.println;

public class ModUnbanCommand implements Command {

    private final AccountHandler accountHandler = AccountHandler.getINSTANCE();

    @Override
    public String getName() {
        return "unban";
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
            println("Usage - unban [id] ");
            return;
        }

        accountHandler.modUnbanAccount(Long.parseLong(argument));
    }
}