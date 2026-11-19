package org.crudfeed.cli.menu;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.command.impl.account.AccountCommand;
import org.crudfeed.cli.command.impl.post.PostCommand;
import org.crudfeed.cli.command.impl.system.*;
import org.crudfeed.cli.command.impl.message.MessageCommand;
import org.crudfeed.cli.network.UpdateClient;
import org.crudfeed.cli.session.Session;

import java.util.HashMap;
import java.util.Map;

import static java.lang.IO.*;

public class MenuRouter {

    private final Session session = Session.getINSTANCE();
    private final UpdateClient updateClient = UpdateClient.getINSTANCE();

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
        Command statCommand = new StatusCommand();
        register(statCommand);
        commands.put("stat", statCommand);

        // Whoami
        Command wmCommand = new WhoamiCommand();
        register(wmCommand);
        commands.put("wm", wmCommand);

        // Change server
        Command changeServerCommand = new ChangeServerCommand();
        register(changeServerCommand);
        commands.put("cs", changeServerCommand);

        // Quit
        Command quitCommand = new QuitCommand();
        register(quitCommand);
        commands.put("q", quitCommand);
    }

    public void run() {

        //Type of ASCII art: STANDARD
        String asciiArt = """
                  ____                _ _____             _    ____ _     ___   _____        ___ \s
                 / ___|_ __ _   _  __| |  ___|__  ___  __| |  / ___| |   |_ _| |___ /       / _ \\\s
                | |   | '__| | | |/ _` | |_ / _ \\/ _ \\/ _` | | |   | |    | |    |_ \\      | | | |
                | |___| |  | |_| | (_| |  _|  __/  __/ (_| | | |___| |___ | |   ___) |  _  | |_| |
                 \\____|_|   \\__,_|\\__,_|_|  \\___|\\___|\\__,_|  \\____|_____|___| |____/  (_)  \\___/\s
                
                """;

        println(asciiArt);

        updateClient.checkAndDownload();

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