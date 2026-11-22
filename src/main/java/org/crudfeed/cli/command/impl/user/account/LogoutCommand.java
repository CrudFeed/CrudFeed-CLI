package org.crudfeed.cli.command.impl.user.account;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.session.Session;

public class LogoutCommand implements Command {

    private final Session session = Session.getINSTANCE();

    @Override
    public String getName() {
        return "logout";
    }

    @Override
    public boolean requiresAuth() {
        return true;
    }

    @Override
    public void execute(String argument) {
            session.logout();
    }
}