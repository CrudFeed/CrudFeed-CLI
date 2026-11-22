package org.crudfeed.cli.command.impl.user.post;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.PostHandler;

import static java.lang.IO.*;

public class LikeCommand implements Command {

    private final PostHandler postHandler = PostHandler.getINSTANCE();

    @Override
    public String getName() {
        return "like";
    }

    @Override
    public boolean requiresAuth() {
        return true;
    }

    @Override
    public void execute(String argument) {
        String input = argument.trim();

        if (input.isEmpty()) {println("Usage - like [id]"); return;}

        postHandler.likePost(Long.parseLong(input));
    }
}