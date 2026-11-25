package org.crudfeed.cli.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.command.impl.mod.*;
import org.crudfeed.cli.command.impl.user.account.AccountCommand;
import org.crudfeed.cli.command.impl.user.system.*;
import org.crudfeed.cli.command.impl.user.post.PostCommand;
import org.crudfeed.cli.command.impl.user.message.MessageCommand;
import org.crudfeed.cli.handler.AccountHandler;
import org.crudfeed.cli.session.Session;
import org.crudfeed.core.dto.GetBannedRequestDto;
import org.crudfeed.core.entity.Role;
import org.crudfeed.core.entity.User;

import java.util.HashMap;
import java.util.Map;

import static java.lang.IO.*;

public class MenuRouter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<String, Command> commands = new HashMap<>();

    private static final String VERSION = "3.0";
    private static final String WEB_URL = "https://crudfeed.github.io/";
    private static final String RELEASE_URL = "https://github.com/CrudFeed/CrudFeed-CLI/releases";

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
        Command messageCommand = new MessageCommand();
        register(messageCommand);
        commands.put("msg", messageCommand);

        // Mod
        Command modCommand = new ModCommand();
        register(modCommand);
        commands.put("m", modCommand);

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

        // Type of ASCII art: STANDARD
        String asciiArt = """
          ____                _ _____             _    ____ _     ___   _____        ___
         / ___|_ __ _   _  __| |  ___|__  ___  __| |  / ___| |   |_ _| |___ /       / _ \\
        | |   | '__| | | |/ _` | |_ / _ \\/ _ \\/ _` | | |   | |    | |    |_ \\      | | | |
        | |___| |  | |_| | (_| |  _|  __/  __/ (_| | | |___| |___ | |   ___) |  _  | |_| |
         \\____|_|   \\__,_|\\__,_|_|  \\___|\\___|\\__,_|  \\____|_____|___| |____/  (_)  \\___/
        """;

        println(asciiArt);
        println("Version:  " + VERSION);
        println("Website:  " + WEB_URL);
        println("Release:  " + RELEASE_URL);
        println();

        Session session = Session.getINSTANCE();
        AccountHandler accountHandler = AccountHandler.getINSTANCE();

        // Main
        while (!session.isExit()) {
            try {
                String input = readln("❯ ").trim();
                if (input.isBlank()) continue;

                String[] parts = input.split("\\s+", 2);
                String commandName = parts[0].toLowerCase();
                String argument = parts.length > 1 ? parts[1] : "";

                Command command = commands.get(commandName);

                User currentUser = session.getCurrentUser();

                if (command == null) {
                    println("Invalid command: \"" + commandName + "\". Type help for list of commands.");
                    continue;
                }

                if (session.isLoggedIn()) {
                    try {
                        String isBanned = accountHandler.getBanned(session.getCurrentUser().getId());
                        GetBannedRequestDto userInfo = objectMapper.readValue(isBanned, GetBannedRequestDto.class);

                        if (userInfo.banned()) {
                            session.logout();
                            println("Your account has been banned. You have been logged out.");
                            continue;
                        }
                    } catch (Exception e) {
                        println("Something went wrong: " + e.getMessage());
                    }
                }

                if (command.requiresAuth() && (currentUser == null || !session.isLoggedIn())) {
                    println("You are not logged in!");
                    continue;
                }

                Role required = command.getRequiredRole();
                if (required != null) {
                    if (!session.isLoggedIn()) {
                        println("You are not logged in!");
                        continue;
                    }
                    if (!session.getCurrentUser().getRole().hasAtLeast(required)) {
                        println("You are not allowed to use this command!");
                        continue;
                    }
                }

                command.execute(argument);
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