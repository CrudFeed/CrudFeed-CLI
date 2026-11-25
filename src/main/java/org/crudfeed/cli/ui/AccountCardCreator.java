package org.crudfeed.cli.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.crudfeed.core.dto.account.ViewAccountPageDto;

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
            ViewAccountPageDto userInfo = objectMapper.readValue(jsonResponse, ViewAccountPageDto.class);

            DateTimeFormatter formatter = dateFormatter.withZone(ZoneId.systemDefault());

            String lastSeen = userInfo.lastSeen() != null
                    ? formatter.format(userInfo.lastSeen())
                    : "N/A";

            String createdAt = userInfo.createdAt() != null
                    ? formatter.format(userInfo.createdAt())
                    : "N/A";

            return "\nID: " + userInfo.id() +
                    "\nName: " + userInfo.nickname() +
                    "\nSubscribers: " + userInfo.subscribers() +
                    "\nSubscriptions: " + userInfo.subscriptions() +
                    "\nBio: " + userInfo.bio() +
                    "\nLast seen: " + lastSeen +
                    "\nCreated at: " + createdAt +
                    "\n";
        } catch (Exception e) {
            return "Failed to parse page: " + e.getMessage();
        }
    }
}