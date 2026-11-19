package org.crudfeed.cli.command.impl.account;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.AccountHandler;

import static java.lang.IO.*;

public class LoginCommand implements Command {

    private final AccountHandler accountHandler = AccountHandler.getINSTANCE();

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public void execute(String argument) {
        String input = argument.trim();

        if (input.isEmpty()) {
            println("Usage - login [email] [password]");
            return;
        }

        String[] credentials = input.split("\\s+", 2);

        if (credentials.length < 2) {
            println("Missing argument. Usage - login [email] [password]");
            return;
        }

        String email = credentials[0];
        String password = credentials[1];

        accountHandler.signIn(email, password);

    }
}