package org.crudfeed.cli.command.impl.mod.system;

import org.crudfeed.cli.command.Command;
import org.crudfeed.core.entity.Role;

import static java.lang.IO.println;

public class ModHelpCommand implements Command {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public Role getRequiredRole() {
        return Role.MODERATOR;
    }

    @Override
    public void execute(String argument) {
        println("""
                Available commands:
                ============================================================================================================================
                [NAME]                [ALIAS]        [ARGUMENTS]                               [INFORMATION]                  [READY TO USE]
                ============================================================================================================================
                
                >> ACCOUNT -----------------------------------------------------------------------------------------------------------------
                mod ban               / m ban        [id]                                      - ban account
                mod unban             / m unban      [id]                                      - unban account
                mod account-delete    / m ac-del     [id]                                      - delete account
                mod account-restore   / m ac-res     [id]                                      - restore account
                mod promote           / m promote    [id]                                      - promote account
                mod demote            / m demote     [id]                                      - demote account
                mod show              / m show       [id]                                      - show full account info
                mod ---               ---            ---                                       ---
                mod ---               ---            ---                                       ---
                mod ---               ---            ---                                       ---
                mod ---               ---            ---                                       ---
                
                >> POST --------------------------------------------------------------------------------------------------------------------
                mod post-delete       / m p-del      [id]                                      - delete post
                mod post-restore      / m p-res      [id]                                      - restore post
                mod ---               ---            ---                                       ---
                mod ---               ---            ---                                       ---
                mod ---               ---            ---                                       ---
                mod ---               ---            ---                                       ---
                mod ---               ---            ---                                       ---
                mod ---               ---            ---                                       ---
                mod ---               ---            ---                                       ---
                mod ---               ---            ---                                       ---
                
                >> MESSAGES ----------------------------------------------------------------------------------------------------------------
                mod ---               ---            ---                                       ---
                mod ---               ---            ---                                       ---
                mod ---               ---            ---                                       ---
                mod ---               ---            ---                                       ---
                
                >> SYSTEM ------------------------------------------------------------------------------------------------------------------
                mod help              / m h                                                    - list of commands
                mod ---               ---            ---                                       ---
                mod ---               ---            ---                                       ---
                mod ---               ---            ---                                       ---
                mod ---               ---            ---                                       ---
                
                ============================================================================================================================
                """);
    }
}