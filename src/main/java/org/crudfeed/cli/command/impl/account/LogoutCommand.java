package org.crudfeed.cli.command.impl.account;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.session.Session;

import static java.lang.IO.*;

public class LogoutCommand implements Command {

    private final Session session = Session.getINSTANCE();

    @Override
    public String getName() {
        return "logout";
    }

    @Override
    public void execute(String argument) {
        if (session.isLoggedIn()) {
            println("Successfully logged out!");
            session.logout();
        } else {
            println("You are not logged in!");
        }
    }
}