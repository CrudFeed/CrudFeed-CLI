package org.crudfeed.cli.command.impl.user.message;

import org.crudfeed.cli.command.Command;

import java.util.HashMap;
import java.util.Map;

import static java.lang.IO.*;

public class MessageCommand implements Command {
    private final Map<String, Command> subCommands = new HashMap<>();

    public  MessageCommand() {
        subCommands.put("show", new ShowCommand());
        subCommands.put("write", new WriteCommand());
        subCommands.put("edit", new EditCommand());
        subCommands.put("delete", new DeleteCommand());
    }

    @Override
    public String getName() {
        return "message";
    }

    @Override
    public void execute(String argument) {
        String trimmed = argument.trim();

        if (trimmed.isEmpty()) {
            println("Usage message / msg [show | write | edit | delete]");
            return;
        }

        String[] parts = trimmed.split("\\s+", 2);
        String subCommandName = parts[0].toLowerCase();
        String remainingArgs = parts.length > 1 ? parts[1] : "";

        Command subCommand = subCommands.get(subCommandName);

        if (subCommand == null) {
            println("Unknown message sub-command: '" + subCommandName + "'. Try: show, write, edit, delete");
            return;
        }

        subCommand.execute(remainingArgs);
    }
}