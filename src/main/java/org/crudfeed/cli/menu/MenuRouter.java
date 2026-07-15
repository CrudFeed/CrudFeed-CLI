package org.crudfeed.cli.menu;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.command.impl.account.AccountCommand;
import org.crudfeed.cli.command.impl.post.PostCommand;
import org.crudfeed.cli.command.impl.system.StatusCommand;
import org.crudfeed.cli.command.impl.system.WhoamiCommand;
import org.crudfeed.cli.command.impl.message.MessageCommand;
import org.crudfeed.cli.command.impl.post.*;
import org.crudfeed.cli.command.impl.system.HelpCommand;
import org.crudfeed.cli.command.impl.system.QuitCommand;
import org.crudfeed.core.entity.Session;

import java.util.HashMap;
import java.util.Map;

import static java.lang.IO.*;

public class MenuRouter {

    private final Session session = Session.getINSTANCE();

    private final Map<String, Command> commands = new HashMap<>();

    public MenuRouter() {
        // Create command "account" with alias "ac"
        Command accountCommand = new AccountCommand();
        register(accountCommand);
        commands.put("ac", accountCommand);

        // Post
        Command postCommand = new PostCommand();
        register(postCommand);
        commands.put("p", postCommand);

        // Message
        Command MessageCommand = new MessageCommand();
        register(MessageCommand);
        commands.put("msg", MessageCommand);

        // Help
        Command helpCommand = new HelpCommand();
        register(helpCommand);
        commands.put("h", helpCommand);

        // Status
        Command statCmd = new StatusCommand();
        register(statCmd);
        commands.put("stat", statCmd);

        // Whoami
        Command wmCmd = new WhoamiCommand();
        register(wmCmd);
        commands.put("wm", wmCmd);

        // Quit
        Command quitCmd = new QuitCommand();
        register(quitCmd);
        commands.put("q", quitCmd);
    }

    public void run() {

        //Type of ASCII art: STANDARD
        String asciiArt = """
                   ____                _ _____             _    ____ _     ___   ____         ___ \s
                  / ___|_ __ _   _  __| |  ___|__  ___  __| |  / ___| |   |_ _| |___ \\       / _ \\\s
                 | |   | '__| | | |/ _` | |_ / _ \\/ _ \\/ _` | | |   | |    | |    __) |     | | | |
                 | |___| |  | |_| | (_| |  _|  __/  __/ (_| | | |___| |___ | |   / __/   _  | |_| |
                  \\____|_|   \\__,_|\\__,_|_|  \\___|\\___|\\__,_|  \\____|_____|___| |_____| (_)  \\___/\s
                
                """;

        println(asciiArt);

        // Main
        while (!session.isExit()) {
            try {
                String input = readln("❯ ").trim();
                if (input.isBlank()) continue;

                String[] parts = input.split("\\s+", 2);
                String commandName = parts[0].toLowerCase();
                String argument = parts.length > 1 ? parts[1] : "";

                Command command = commands.get(commandName);

                if (command != null) {
                    command.execute(argument);
                } else {
                    println("Invalid command: " + "\"" + commandName + "\". " + "Type help for list of commands.");
                }
            } catch (Exception e) {
                println("Something went wrong: " + e.getMessage());
            }
        }
    }

    // Create commands
    private void register(Command cmd) {
        commands.put(cmd.getName().toLowerCase(), cmd);
    }
}