package org.crudfeed.cli.command.impl.user.post;

import org.crudfeed.cli.command.Command;

import static java.lang.IO.*;

public class SearchCommand implements Command {

    @Override
    public String getName() {
        return "search";
    }

    @Override
    public void execute(String argument) {
        String[] args = argument.trim().split("\\s+");

        if (argument == null || argument.trim().isEmpty()) {
            println("Usage: post search -[argument] [id]");
            return;
        }

        if (args.length < 2) {
            println("Usage: post search -[argument] [id]");
            return;
        }

        switch (args[0]) {
            case "-i" -> new SearchByIdCommand().execute(args[1]);

            case "-w" -> new SearchByWordsCommand().execute(args[1]);

            default -> System.out.println("Unknown search option: " + args[0]);
        }
    }
}