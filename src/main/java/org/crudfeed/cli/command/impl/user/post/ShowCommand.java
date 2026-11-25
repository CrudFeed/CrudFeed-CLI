package org.crudfeed.cli.command.impl.user.post;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.PostHandler;
import org.crudfeed.cli.ui.PostCardCreator;

import static java.lang.IO.*;

public class ShowCommand implements Command {

    private final PostHandler postHandler = PostHandler.getINSTANCE();
    private final PostCardCreator postCardCreator = PostCardCreator.getINSTANCE();

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public boolean requiresAuth() {
        return true;
    }

    @Override
    public void execute(String argument) {
        println(postCardCreator.createPostCard(postHandler.showUserPostsById()));
    }
}