package org.crudfeed.cli.command.impl.post;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.PostHandler;

import static java.lang.IO.*;

public class DislikeCommand implements Command {

    private final PostHandler postHandler = PostHandler.getINSTANCE();

    @Override
    public String getName() {
        return "dislike";
    }

    @Override
    public void execute(String argument) {
        String input = argument.trim();

        if (input.isEmpty()) {println("Usage - dislike [id]"); return;}

        postHandler.disLikePost(input);
    }
}