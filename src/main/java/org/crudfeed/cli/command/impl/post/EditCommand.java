package org.crudfeed.cli.command.impl.post;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.PostHandler;

import static java.lang.IO.*;


public class EditCommand implements Command {

    private final PostHandler postHandler = PostHandler.getINSTANCE();

    @Override
    public String getName() {
        return "edit";
    }

    @Override
    public void execute(String argument) {
        String input = argument.trim();

        if (input.isEmpty()) {println("Usage - edit [id] [text]"); return;}

        String[] credentials = input.split("\\s+", 2);

        if (credentials.length < 2) {
            println("Missing argument. Usage - edit [id] [text]");
        }

        String postId = credentials[0];
        String content = credentials[1];

        postHandler.editPost(postId, content);


    }
}