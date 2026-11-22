package org.crudfeed.cli.command.impl.user.post;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.PostHandler;

public class CreateCommand implements Command {

    private final PostHandler postHandler = PostHandler.getINSTANCE();

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public boolean requiresAuth() {
        return true;
    }

    @Override
    public void execute(String argument) {
        postHandler.createPost(argument);
    }
}