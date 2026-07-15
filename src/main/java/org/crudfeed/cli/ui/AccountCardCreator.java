package org.crudfeed.cli.ui;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountCardCreator {

    @Getter
    private static final AccountCardCreator INSTANCE = new AccountCardCreator();

    public String createAccountCard(String response) {
        if (response.equals("EMPTY")) {
            return "The page is empty!";
        }

        String[] user = response.split("\\|", -1);

        return "\nID: " + user[0] +
                "\nNickname: " + user[1] +
                "\nSubscribers: " + user[2] +
                "\nSubscriptions: " + user[3] +
                "\nBio: " + user[4];
    }
}