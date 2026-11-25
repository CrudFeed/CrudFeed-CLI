package org.crudfeed.cli.session;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.crudfeed.core.entity.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Session {

    @Getter
    private static final Session INSTANCE = new Session();

    @Getter
    private User currentUser;

    @Getter
    private final long guestId = java.util.concurrent.ThreadLocalRandom.current().nextLong(2000000000);

    @Getter @Setter
    private boolean exit = false;

    @Getter @Setter
    private String token;

    public boolean isLoggedIn() {
        return currentUser != null && token != null && !token.isBlank();
    }

    public void login(User user, String token) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot login with null user!");
        }
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Cannot login with empty token!");
        }
        this.currentUser = user;
        this.token = token;
    }

    public void logout() {
        this.currentUser = null;
        this.token = null;
    }
}