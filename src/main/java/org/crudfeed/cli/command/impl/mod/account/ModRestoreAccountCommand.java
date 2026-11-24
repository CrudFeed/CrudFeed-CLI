package org.crudfeed.cli.command.impl.mod.account;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.ModHandler;
import org.crudfeed.cli.session.Session;
import org.crudfeed.core.entity.Role;

import static java.lang.IO.println;

public class ModRestoreAccountCommand implements Command {

    private final ModHandler modHandler = ModHandler.getINSTANCE();

    @Override
    public String getName() {
        return "account-restore";
    }

    @Override
    public Role getRequiredRole() {
        return Role.ADMIN;
    }

    @Override
    public boolean requiresAuth() {
        return true;
    }

    @Override
    public void execute(String argument) {
        if (argument.isEmpty()) {
            println("Usage - account-restore [id] ");
            return;
        }

        modHandler.modAction(
                Session.getINSTANCE().getCurrentUser().getId(),
                Long.parseLong(argument),
                "modRestoreAccount",
                "user",
                "restore this account",
                "Restore cancelled."
        );    }
}