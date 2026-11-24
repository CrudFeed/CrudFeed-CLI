package org.crudfeed.cli.command.impl.user.account;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.AccountHandler;
import org.crudfeed.cli.session.Session;

public class AddFriendCommand implements Command {

    AccountHandler accountHandler = AccountHandler.getINSTANCE();
    Session session = Session.getINSTANCE();

    @Override
    public String getName() {
        return "add-friend";
    }

    @Override
    public boolean requiresAuth() {
        return true;
    }

    @Override
    public void execute(String argument) {
        accountHandler.addOrRemoveFriend(session.getCurrentUser().getId(), argument, "add");
    }
}
