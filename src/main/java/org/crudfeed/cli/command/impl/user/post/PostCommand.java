package org.crudfeed.cli.command.impl.user.post;

import org.crudfeed.cli.command.Command;

import java.util.HashMap;
import java.util.Map;

import static java.lang.IO.*;

public class PostCommand implements Command {

    private final Map<String, Command> subCommands = new HashMap<>();

    public PostCommand() {
        subCommands.put("create", new CreateCommand());

        subCommands.put("feed", new FeedCommand());

        subCommands.put("like", new LikeCommand());

        subCommands.put("dislike", new DislikeCommand());

        subCommands.put("likes", new LikesCommand());

        subCommands.put("show", new ShowCommand());

        subCommands.put("search", new SearchCommand());
        subCommands.put("src", subCommands.get("search"));

        subCommands.put("edit", new EditCommand());

        subCommands.put("delete", new DeleteCommand());
        subCommands.put("rm", subCommands.get("delete"));
    }

    @Override
    public String getName() {
        return "post";
    }

    @Override
    public void execute(String argument) {
        String trimmed = argument.trim();

        if (trimmed.isEmpty()) {
            println("Usage: post [create | edit | delete | feed | like | unlike | likes | search -i, search -w]");
            return;
        }

        String[] parts = trimmed.split("\\s+", 2);
        String subCommandName = parts[0].toLowerCase();
        String remainingArgs = parts.length > 1 ? parts[1] : "";

        Command subCommand = subCommands.get(subCommandName);

        if (subCommand == null) {
            println("Unknown post sub-command: '" + subCommandName + "'. Try: create, edit, delete, like...");
            return;
        }

        subCommand.execute(remainingArgs);
    }
}