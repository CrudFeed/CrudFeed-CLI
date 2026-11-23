package org.crudfeed.cli.handler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.crudfeed.cli.session.Session;
import org.crudfeed.cli.network.ResponseClient;
import org.crudfeed.core.dto.*;
import org.crudfeed.core.entity.User;

import static java.lang.IO.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountHandler {

    @Getter
    private static final AccountHandler INSTANCE = new AccountHandler();

    private final Session session = Session.getINSTANCE();
    private final ResponseClient api = ResponseClient.getINSTANCE();

    public void signUp(String email, String nickname, String displayName, String password) {
        if (session.isLoggedIn()) {
            println("You are already logged in as @" + session.getCurrentUser().getNickname() + "!");
            return;
        }

        println("Connecting to CrudFeed CLI...");

        try {
            SignUpRequestDto dto = new SignUpRequestDto(email, nickname, displayName, password);

            String response = api.sendPost("sign-up", dto);

            if (response == null || response.startsWith("Error")) {
                println(response != null ? response : "Error: Empty response from server");
                return;
            }

            AuthResultDto result = api.parseUser(response);
            if (result == null) {
                println("Error: Failed to parse auth response.");
                return;
            }

            if (!result.ok()) {
                println("Error: " + result.error());
                return;
            }

            UserResponseDto userDto = result.user();
            if (userDto == null) {
                println("Error: Missing user data in response.");
                return;
            }

            User user = new User();
            user.setId(userDto.id());
            user.setEmail(userDto.email());
            user.setNickname(userDto.nickname());
            user.setDisplayName(userDto.displayName());
            user.setRole(userDto.role());

            session.login(user);
            println("Successfully logged in as @" + session.getCurrentUser().getNickname() + "!");

        } catch (Exception e) {
            println("Connection error: " + e.getMessage());
        }
    }

    public void signIn(String email, String password) {
        if (session.isLoggedIn()) {
            println("You are already logged in as @" + session.getCurrentUser().getNickname() + "!");
            return;
        }

        println("Connecting to CrudFeed CLI...");

        try {
            SignInRequestDto dto = new SignInRequestDto(email, password);

            String response = api.sendPost("sign-in", dto);

            if (response == null || response.startsWith("Error")) {
                println(response != null ? response : "Error: Empty response from server");
                return;
            }

            AuthResultDto result = api.parseUser(response);
            if (result == null) {
                println("Error: Failed to parse auth response.");
                return;
            }

            if (!result.ok()) {
                println("Error: " + result.error());
                return;
            }

            UserResponseDto userDto = result.user();
            if (userDto == null) {
                println("Error: Missing user data in response.");
                return;
            }

            User user = new User();
            user.setId(userDto.id());
            user.setEmail(userDto.email());
            user.setNickname(userDto.nickname());
            user.setDisplayName(userDto.displayName());
            user.setRole(userDto.role());

            session.login(user);
            println("Successfully logged in as @" + session.getCurrentUser().getNickname() + "!");

        } catch (Exception e) {
            println("Connection error: " + e.getMessage());
        }
    }

    public String getBanned(long userId) {
        GetBannedDtoRequest dto = new GetBannedDtoRequest(userId, false);
        return api.sendPost("getBanned", dto);
    }

    public void status() {
        String response = api.sendGet("status");

        println("Server status: " + response);

        println("Server URL: " + ResponseClient.getBaseUrl());

        if (session.isLoggedIn()) {
            println("You are logged in as: @" + session.getCurrentUser().getNickname());
            println("Your display name is: " + session.getCurrentUser().getDisplayName());
            println("Welcome back!");
        } else {
            println("Current user status: @guest" + session.getGuestId());
            println("You are not authenticated. Use 'ac login' or 'ac reg' command.");
        }
    }

    public void changeServer(String server) {
        ResponseClient.setBaseUrl(server);
        println("Changing server status: " + server);
    }

    public void whoami() {
        if (session.isLoggedIn()) {
            println("You are logged in as @" + session.getCurrentUser().getNickname());
            println("Your display name is: " + session.getCurrentUser().getDisplayName());
            println("Welcome back!");
        } else {
            println("Current user status: @guest" + session.getGuestId());
            println("You are not authenticated. Use 'ac login' or 'ac reg' command.");
        }
    }

    public void subscribe(String nickname) {
        long currentUserId = session.getCurrentUser().getId();

        SubscribeRequestDto dto = new SubscribeRequestDto(currentUserId, nickname);

        String response = api.sendPost("subscribe", dto);

        println(response);
    }

    public void unSubscribe(String nickname) {
        long currentUserId = session.getCurrentUser().getId();

        SubscribeRequestDto dto = new SubscribeRequestDto(currentUserId, nickname);

        String response = api.sendPost("unsubscribe", dto);

        println(response);
    }

    public String viewAccountPage(String nickname) {
        ViewAccountPageRequestDto dto = new ViewAccountPageRequestDto(
                0L,
                nickname,
                0L,
                0L,
                null,
                null,
                null
        );

        return api.sendPost("viewAccountPage", dto);
    }

    public void editAccount(String content) {
        long currentUserId = session.getCurrentUser().getId();

        EditAccountPageRequestDto dto = new EditAccountPageRequestDto(currentUserId, content);

        String response = api.sendPost("editAccountPage", dto);

        println(response);
    }

    public void changeNickname(String nickname) {
        long currentUserId = session.getCurrentUser().getId();

        ChangeNicknameRequestDto dto = new ChangeNicknameRequestDto(currentUserId, nickname);

        String response = api.sendPost("changeNickname", dto);

        if (response.contains("successfully")) {
            session.getCurrentUser().setNickname(nickname);
        }

        println(response);
    }

    public void changePassword(String password) {
        long currentUserId = session.getCurrentUser().getId();

        ChangePasswordRequestDto dto = new ChangePasswordRequestDto(currentUserId, password);

        String response = api.sendPost("changePassword", dto);

        if (response.contains("successfully")) {
            session.getCurrentUser().setPassword(password);
        }

        println(response);
    }

    public void deleteAccount() {
        String confirm = readln("Are you sure you want to delete this account? (y/n): ");
        if (!confirm.equalsIgnoreCase("y")) {
            println("Deletion cancelled.");
            return;
        }

        long currentUserId = session.getCurrentUser().getId();

        AccountIdRequestDto dto = new AccountIdRequestDto(currentUserId);

        String response = api.sendPost("deleteAccount", dto);

        if (response.equals("Account successfully deleted!")) {
            session.logout();
        }

        println(response);
    }

    public String getNicknameById(long userId) {
        AccountIdRequestDto dto = new AccountIdRequestDto(userId);

        return api.sendPost("getUserById", dto);
    }

    public void modBanAccount(long userId) {
        if (userId < 1) {
            println("User ID cannot be empty!");
            return;
        }

        String confirm = readln("Are you sure you want to ban this account? (y/n): ");

        if (!confirm.equalsIgnoreCase("y")) {
            println("Ban cancelled.");
            return;
        }

        String response = api.sendPost("modBanAccount", userId);
        println(response);
    }

    public void modUnbanAccount(long userId) {
        if (userId < 1) {
            println("User ID cannot be empty!");
            return;
        }

        String confirm = readln("Are you sure you want to unban this account? (y/n): ");

        if (!confirm.equalsIgnoreCase("y")) {
            println("Unban cancelled.");
            return;
        }

        String response = api.sendPost("modUnbanAccount", userId);
        println(response);
    }
}