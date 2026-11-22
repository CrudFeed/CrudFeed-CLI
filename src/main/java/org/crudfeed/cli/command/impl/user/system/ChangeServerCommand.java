package org.crudfeed.cli.command.impl.user.system;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.AccountHandler;

import static java.lang.IO.*;

public class ChangeServerCommand implements Command {

    private final AccountHandler accountHandler = AccountHandler.getINSTANCE();

    @Override
    public String getName() {
        return "change-server";
    }

    @Override
    public void execute(String argument) {
        String trimed = argument.trim();

        if (!trimed.startsWith("http://") && !trimed.startsWith("https://")) {
            println("Usage: change-server [ip:port]");
            println("Example №1: change-server http://localhost:8080/api");
            println("Example №2: change-server http://100.65.191.8:8080/api");
            return;
        }

        if (!trimed.equals("http://100.65.191.8:8080/api")) {
            println("WARNING! Official server is \"http://100.65.191.8:8080/api\"!");
        }

        accountHandler.changeServer(argument);
    }
}