package org.crudfeed.cli.command.impl.account;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.AccountHandler;

import static java.lang.IO.println;

public class RegistrationCommand implements Command {

    private final AccountHandler accountHandler = AccountHandler.getINSTANCE();

    @Override
    public String getName() {
        return "registration";
    }

    @Override
    public void execute(String argument) {
        String input = argument.trim();

        if (input.isEmpty()) {
            println("Usage: account registration [name] [password]");
            return;
        }

        String[] credentials = input.split("\\s+", 2);

        if (credentials.length < 2) {
            println("Missing password! Usage: account registration [name] [password]");
            return;
        }

        String nickname = credentials[0];
        String password = credentials[1];

        accountHandler.signUp(nickname, password);
    }
}