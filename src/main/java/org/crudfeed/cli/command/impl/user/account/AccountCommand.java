package org.crudfeed.cli.command.impl.user.account;

import org.crudfeed.cli.command.Command;
import java.util.HashMap;
import java.util.Map;
import static java.lang.IO.*;

public class AccountCommand implements Command {

    private final Map<String, Command> subCommands = new HashMap<>();

    public AccountCommand() {
        subCommands.put("name", new NameCommand());
        subCommands.put("password", new PasswordCommand());
        subCommands.put("pass", subCommands.get("password"));

        subCommands.put("show", new ShowCommand());

        subCommands.put("subscribe", new SubscribeCommand());
        subCommands.put("sub", subCommands.get("subscribe"));

        subCommands.put("unsubscribe", new UnsubscribeCommand());
        subCommands.put("unsub", subCommands.get("unsubscribe"));

        subCommands.put("login", new LoginCommand());
        subCommands.put("l", subCommands.get("login"));

        subCommands.put("registration", new RegistrationCommand());
        subCommands.put("reg", subCommands.get("registration"));

        subCommands.put("logout", new LogoutCommand());
        subCommands.put("edit", new EditCommand());
        subCommands.put("delete", new DeleteCommand());
    }

    @Override
    public String getName() {
        return "account";
    }

    @Override
    public void execute(String argument) {
        String trimmed = argument.trim();

        if (trimmed.isEmpty()) {
            println("Usage: account [show | login | logout | edit | delete | name | password]");
            return;
        }

        String[] parts = trimmed.split("\\s+", 2);
        String subCommandName = parts[0].toLowerCase();
        String remainingArgs = parts.length > 1 ? parts[1] : "";

        Command subCommand = subCommands.get(subCommandName);

        if (subCommand == null) {
            println("Unknown account sub-command: '" + subCommandName + "'. Try: show, login, logout...");
            return;
        }

        subCommand.execute(remainingArgs);
    }
}