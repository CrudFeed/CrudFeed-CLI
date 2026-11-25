package org.crudfeed.cli.command.impl.user.post;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.PostHandler;
import org.crudfeed.cli.ui.PostCardCreator;

import static java.lang.IO.*;

public class SearchByIdCommand implements Command {

    private final PostHandler postHandler = PostHandler.getINSTANCE();
    private final PostCardCreator postCardCreator = PostCardCreator.getINSTANCE();

    @Override
    public String getName() {
        return "search -i";
    }

    @Override
    public void execute(String argument) {
        String input = argument.trim();

        if (input.isEmpty()) {
            println("Usage - search -i [id]");
            return;
        }

        println(postCardCreator.createPostCard(postHandler.searchPostById(Long.parseLong(argument))));

    }
}