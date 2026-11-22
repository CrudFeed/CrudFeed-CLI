package org.crudfeed.cli.command.impl.user.account;

import org.crudfeed.cli.command.Command;
import org.crudfeed.cli.ui.AccountCardCreator;
import org.crudfeed.cli.handler.AccountHandler;

import static java.lang.IO.*;

public class ShowCommand implements Command {

    private final AccountHandler accountHandler = AccountHandler.getINSTANCE();
    private final AccountCardCreator accountCardCreator = AccountCardCreator.getINSTANCE();


    @Override
    public String getName() {
        return "show";
    }

    @Override
    public void execute(String argument) {
        String input = argument.trim();

        if (input.isEmpty()) {
            println("Usage: account show [username]");
            return;
        }

        println(accountCardCreator.createAccountCard(accountHandler.viewAccountPage(input)));
    }
}