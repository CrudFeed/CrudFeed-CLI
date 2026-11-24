package org.crudfeed.cli.command.impl.user.account;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.AccountHandler;
import org.crudfeed.cli.session.Session;
import org.crudfeed.cli.ui.FriendsListCardCreator;

import static java.lang.IO.println;

public class ShowFriendsListCommand implements Command {

    FriendsListCardCreator friendsListCardCreator = FriendsListCardCreator.INSTANCE;
    AccountHandler accountHandler = AccountHandler.getINSTANCE();
    Session session = Session.getINSTANCE();

    @Override
    public String getName() {
        return "friends";
    }

    @Override
    public boolean requiresAuth() {
        return true;
    }

    @Override
    public void execute(String argument) {
        println(friendsListCardCreator.createFriendListCard(accountHandler.showFriendsList(
                session.getCurrentUser().getId(),
                0,
                "",
                false
                ))
        );
    }
}
