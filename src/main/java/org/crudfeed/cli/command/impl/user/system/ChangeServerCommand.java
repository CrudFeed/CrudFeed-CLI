package org.crudfeed.cli.command.impl.user.system;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.AccountHandler;
import org.crudfeed.cli.network.ResponseClient;

import static java.lang.IO.*;

public class ChangeServerCommand implements Command {

    private final AccountHandler accountHandler = AccountHandler.getINSTANCE();

    @Override
    public String getName() {
        return "change-server";
    }

    @Override
    public void execute(String argument) {
        if (argument == null) {
            println("Invalid argument!");
            return;
        }

        String trimed = argument.trim();

        if (!trimed.startsWith("http://") && !trimed.startsWith("https://")) {
            println("Usage: change-server [http(s)://ip:port/api]");
            println("Example №1: change-server " + ResponseClient.getTAILSCALE());
            println("Example №2: change-server " + ResponseClient.getLOCALHOST());
            return;
        }

        if (!trimed.equals(ResponseClient.getTAILSCALE())) {
            println("WARNING! Official server is: " +  ResponseClient.getTAILSCALE());
        }

        accountHandler.changeServer(trimed);
    }
}