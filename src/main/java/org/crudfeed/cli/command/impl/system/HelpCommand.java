package org.crudfeed.cli.command.impl.system;

import org.crudfeed.cli.command.Command;

import static java.lang.IO.*;

public class HelpCommand implements Command {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void execute(String argument) {
        println("""
                Available commands:
                ============================================================================================================================
                [NAME]                [ALIAS]        [ARGUMENTS]                               [INFORMATION]                  [READY TO USE]
                ============================================================================================================================
                
                >> ACCOUNT -----------------------------------------------------------------------------------------------------------------
                account show          / ac show      [name]                                    - show account by name
                account login         / ac l         [email] [password]                        - login into account
                account registration  / ac reg       [email] [name] [display name] [pass]      - create new account
                account logout        / ac logout                                              - logout from account
                account email         / ac email     [new email]                               - update account email
                account name          / ac name      [new name]                                - update account name
                account password      / ac pass      [new pass]                                - update account password
                account edit          / ac edit      [text]                                    - update your bio
                account subscribe     / ac sub       [name]                                    - subscribe to user
                account unsubscribe   / ac unsub     [name]                                    - unsubscribe from user
                account delete        / ac rm        [id]                                      - delete your account
                
                >> POST --------------------------------------------------------------------------------------------------------------------
                post show             / p show                                                 - view your posts
                post feed             / p feed                                                 - view feed
                post create           / p create     [text]                                    - create post
                post edit             / p edit       [text]                                    - update your post
                post delete           / p rm         [id]                                      - delete your post
                post like             / p like       [id]                                      - like post
                post dislike          / p dislike    [id]                                      - dislike post
                post likes            / p likes                                                - view posts you liked
                post search -i        / p src -i     [id]                                      - search posts by ID
                post search -w        / p src -w     [words]                                   - search posts by words
                
                >> MESSAGES ----------------------------------------------------------------------------------------------------------------
                message show          / msg show     [id]                                      - view your messages
                message write         / msg write    [name] [text]                             - send direct message
                message edit          / msg edit     [id] [text]                               - update your message
                message delete        / msg rm       [id]                                      - delete your message
                
                >> SYSTEM ------------------------------------------------------------------------------------------------------------------
                help                  / h                                                      - list of commands
                status                / stat                                                   - server status
                whoami                / wm                                                     - user status
                change-server         / cs           [ip:port]                                 - change server
                quit                  / q                                                      - quit from app
                
                ============================================================================================================================
                """);
    }
}