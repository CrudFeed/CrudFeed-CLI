package org.crudfeed.cli.command.impl.user.post;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.PostHandler;

public class DeleteCommand implements Command {

    private final PostHandler postHandler = PostHandler.getINSTANCE();

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public boolean requiresAuth() {
        return true;
    }

    @Override
    public void execute(String argument) { postHandler.deletePost(Long.parseLong(argument)); }
}