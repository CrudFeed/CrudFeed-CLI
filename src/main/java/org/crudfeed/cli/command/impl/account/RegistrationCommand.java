package org.crudfeed.cli.command.impl.account;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.handler.AccountHandler;

import java.util.Arrays;

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
            println("Usage: account registration [email] [name] [display name] [password]");
            return;
        }

        String[] credentials = input.split("\\s+");

        if (credentials.length < 3) {
            println("Missing password! Usage: account registration [email] [name] [display name] [password]");
            return;
        }

        if (!credentials[0].contains("@") && !credentials[0].contains(".")) {
            println("Invalid email address!");
            return;
        }

        String email = credentials[0];
        String nickname = credentials[1];
        String password = credentials[credentials.length - 1];
        String displayName = String.join(" ", Arrays.copyOfRange(credentials, 2, credentials.length - 1));

        if (nickname.contains(" ") || !nickname.equals(nickname.toLowerCase())) {
            println("Nickname must be lowercase and without spaces!");
            return;
        }

        if (password.length() < 8) {
            println("Password must be at least 8 characters long!");
            return;
        }

        accountHandler.signUp(email, nickname, displayName, password);
    }
}