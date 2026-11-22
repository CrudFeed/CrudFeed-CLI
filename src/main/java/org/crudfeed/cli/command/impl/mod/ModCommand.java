package org.crudfeed.cli.command.impl.mod;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.command.impl.user.account.*;
import org.crudfeed.core.entity.Role;

import java.util.HashMap;
import java.util.Map;

import static java.lang.IO.println;

public class ModCommand implements Command {

    private final Map<String, Command> subCommands = new HashMap<>();

    public ModCommand() {
        subCommands.put("ban", new ModBanCommand());
        subCommands.put("unban", new ModUnbanCommand());

        subCommands.put("help", new ModHelpCommand());
        subCommands.put("h", subCommands.get("help"));
    }

    @Override
    public String getName() {
        return "mod";
    }

    @Override
    public Role getRequiredRole() {
        return Role.MODERATOR;
    }

    @Override
    public void execute(String argument) {
        String trimmed = argument.trim();

        if (trimmed.isEmpty()) {
            println("Usage: mod [ban | unban | help | ...]");
            return;
        }

        String[] parts = trimmed.split("\\s+", 2);
        String subCommandName = parts[0].toLowerCase();
        String remainingArgs = parts.length > 1 ? parts[1] : "";

        Command subCommand = subCommands.get(subCommandName);

        if (subCommand == null) {
            println("Unknown mod sub-command: '" + subCommandName + "'. Try: ban, unban, help...");
            return;
        }

        subCommand.execute(remainingArgs);
    }
}
