package org.crudfeed.cli.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.crudfeed.core.entity.User;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountCardCreator {

    @Getter
    private static final AccountCardCreator INSTANCE = new AccountCardCreator();
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String createAccountCard(String jsonResponse) {
        if (jsonResponse == null || jsonResponse.isBlank() || jsonResponse.startsWith("Error")) {
            return "The page is empty!";
        }

        try {
            User user = objectMapper.readValue(jsonResponse, User.class);

            DateTimeFormatter formatter = dateFormatter.withZone(ZoneId.systemDefault());

            String lastSeen = user.getLastSeen() != null
                    ? formatter.format(user.getLastSeen())
                    : "N/A";

            String createdAt = user.getCreatedAt() != null
                    ? formatter.format(user.getCreatedAt())
                    : "N/A";

            return "ID: " + user.getId() +
                    "\nName: " + user.getNickname() +
                    "\nSubscribers: " + "IN REWORK" +
                    "\nSubscriptions: " + "IN REWORK" +
                    "\nBio: " + user.getBio() +
                    "\nLast seen: " + lastSeen +
                    "\nCreated at: " + createdAt +
                    "\n";
        } catch (Exception e) {
            return "Failed to parse page: " + e.getMessage();
        }
    }
}