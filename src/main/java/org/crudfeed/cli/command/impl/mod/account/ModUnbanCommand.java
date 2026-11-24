package org.crudfeed.cli.command.impl.mod.account;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.ModHandler;
import org.crudfeed.cli.session.Session;
import org.crudfeed.core.entity.Role;

import static java.lang.IO.println;

public class ModUnbanCommand implements Command {

    private final ModHandler modHandler = ModHandler.getINSTANCE();

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

        modHandler.modAction(
                Session.getINSTANCE().getCurrentUser().getId(),
                Long.parseLong(argument),
                "modUnbanAccount",
                "user",
                "unban this account",
                "Unban cancelled."
        );
    }
}