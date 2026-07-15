package org.crudfeed.cli.command;

public interface Command {
    String getName();
    void execute(String argument);
}