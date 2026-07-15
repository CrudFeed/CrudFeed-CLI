package org.crudfeed.cli.command.impl.account;

import org.crudfeed.cli.command.Command;
import org.crudfeed.core.entity.Session;
import org.crudfeed.cli.handler.AccountHandler;

import static java.lang.IO.*;

public class LoginCommand implements Command {

    private final AccountHandler accountHandler = AccountHandler.getINSTANCE();
    private final Session session = Session.getINSTANCE();

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public void execute(String argument) {
        String input = argument.trim();

        if (input.isEmpty()) {
            println("Usage - login [name] [password]");
            return;
        }

        String[] credentials = input.split("\\s+", 2);

        if (credentials.length < 2) {
            println("Missing argument. Usage - login [name] [password]");
            return;
        }

        String nickname = credentials[0];
        String password = credentials[1];

        accountHandler.signIn(nickname, password);

    }
}