package org.crudfeed.cli.handler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.crudfeed.core.entity.Session;
import org.crudfeed.cli.network.ResponseClient;

import static java.lang.IO.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountHandler {

    @Getter
    private static final AccountHandler INSTANCE = new AccountHandler();

    private final Session session = Session.getINSTANCE();
    private final ResponseClient api = ResponseClient.getINSTANCE();

    public boolean signUp(String nickname, String password) { return authenticate("signUp", nickname, password); }

    public boolean signIn(String nickname, String password) { return authenticate("signIn", nickname, password); }

    private boolean authenticate(String action, String nickname, String password) {
        if (session.isLoggedIn()) {
            println("You are already logged in as @" + session.getCurrentUser().getNickname() + "!");
            return false;
        }

        println("Connecting to CrudFeed CLI...");

        try {
            String response = api.sendRequest(action + "|" + nickname + "|" + password);

            if (response.startsWith("Error")) {
                System.out.println(response);
                return false;
            }

            session.login(api.parseUser(response));
            println("Successfully logged in as @" + session.getCurrentUser().getNickname() + "!");
            return true;

        } catch (Exception e) {
            println("Connection error: " + e.getMessage());
            return false;
        }
    }

    public void status() {
        String response = api.sendRequest("status");

        if (response.contains("ONLINE")) {
            println(response);
        } else {
            println("Server status: OFFLINE");
        }

        if (session.isLoggedIn()) {
            println("You are logged in as: @" + session.getCurrentUser().getNickname() + ".");
        } else {
            println("Current user status: @Guest" + session.getGuestId() + ".");
            println("You are not authenticated. Use 'login' or 'reg' command.");
        }
    }

    public void whoami() {
        if (session.isLoggedIn()) {
            println("You are logged in as @" + session.getCurrentUser().getNickname() + ".");
        } else {
            println("Current user status: @Guest" + session.getGuestId());
            println("You are not authenticated. Use 'login' or 'reg' command.");
        }
    }

    public void subscribe(String name) {
        long currentUserId = session.getCurrentUser().getId();

        if (session.isLoggedIn()) {
            String response =  api.sendRequest("subscribe" + "|" + currentUserId + "|" + name);

            println(response);
        } else {
            println("You are not logged in!");
        }
    }

    public void unSubscribe(String name) {
        long currentUserId = session.getCurrentUser().getId();

        if (session.isLoggedIn()) {
            String response =  api.sendRequest("unSubscribe" + "|" + currentUserId + "|" + name);

            println(response);
        } else {
            println("You are not logged in!");
        }
    }

    public String viewAccountPage(String nickname) { return api.sendRequest("viewAccountPage|" + nickname); }

    public void editAccount(String content) {
        long currentUserId = session.getCurrentUser().getId();
        String response = api.sendRequest("editAccountPage|" + currentUserId + "|" + content);

        println(response);
    }

    public void changeNickname(String nickname) {
        long currentUserId = session.getCurrentUser().getId();
        String response = api.sendRequest("changeName|" + nickname + "|" + currentUserId);

        if (response.contains("successfully")) {
            session.getCurrentUser().setNickname(nickname);
        }

        println(response);
    }

    public void changePassword(String password) {
        long currentUserId = session.getCurrentUser().getId();
        String response = api.sendRequest("changePass|" + password + "|" + currentUserId);

        if (response.contains("successfully")) {
            session.getCurrentUser().setPassword(password);
        }

        println(response);
    }

    public void deleteAccount() {
        long currentUserId = session.getCurrentUser().getId();
        String delete = readln("Are you sure you want to delete this account?");
        String response = api.sendRequest("deleteAccount|" + delete + "|" + currentUserId);

        if (response.equals("Account successfully deleted!")) {
            session.logout();
        }

        println(response);
    }

    public String getNicknameById(String userId) { return api.sendRequest("getUserById|" + userId); }
}