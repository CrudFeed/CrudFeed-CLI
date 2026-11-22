package org.crudfeed.cli.command;

import org.crudfeed.core.entity.Role;

public interface Command {
    String getName();
    default boolean requiresAuth() {
        return false;
    }
    default Role getRequiredRole() {
        return null;
    }
    void execute(String argument);
}