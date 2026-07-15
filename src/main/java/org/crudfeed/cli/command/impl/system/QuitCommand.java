package org.crudfeed.cli.command.impl.system;

import org.crudfeed.cli.command.Command;
import org.crudfeed.core.entity.Session;

public class QuitCommand implements Command {

    private final Session session = Session.getINSTANCE();

    @Override
    public String getName() {
        return "quit";
    }

    @Override
    public void execute(String argument) {
        session.logout();
        session.setExit(true);
    }
}