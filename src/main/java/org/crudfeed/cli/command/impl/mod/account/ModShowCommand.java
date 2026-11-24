package org.crudfeed.cli.command.impl.mod.account;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.ModHandler;
import org.crudfeed.cli.session.Session;
import org.crudfeed.cli.ui.ShowInfoCardCreator;
import org.crudfeed.core.entity.Role;

import static java.lang.IO.println;

public class ModShowCommand implements Command {

    private final ModHandler modHandler = ModHandler.getINSTANCE();
    private final ShowInfoCardCreator showInfoCardCreator = ShowInfoCardCreator.getINSTANCE();

    @Override
    public String getName() {
        return "show";
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
            println("Usage - show [id] ");
            return;
        }

        println(showInfoCardCreator.createInfoCard(modHandler.modShow(
                Session.getINSTANCE().getCurrentUser().getId(),
                Long.parseLong(argument)
        )));
    }
}