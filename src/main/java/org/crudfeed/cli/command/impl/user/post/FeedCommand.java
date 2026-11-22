package org.crudfeed.cli.command.impl.user.post;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.ui.PostCardCreator;
import org.crudfeed.cli.handler.PostHandler;

import static java.lang.IO.*;

public class FeedCommand implements Command {

    private final PostHandler postHandler = PostHandler.getINSTANCE();
    private final PostCardCreator postCardCreator = PostCardCreator.getINSTANCE();

    @Override
    public String getName() {
        return "feed";
    }

    @Override
    public void execute(String argument) {
        println(postCardCreator.createPostCard(postHandler.viewFeed()));
    }
}