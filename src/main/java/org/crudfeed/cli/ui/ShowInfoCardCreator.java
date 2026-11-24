package org.crudfeed.cli.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.crudfeed.core.dto.ModShowResponseDto;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShowInfoCardCreator {
    @Getter
    private static final ShowInfoCardCreator INSTANCE = new ShowInfoCardCreator();
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String createInfoCard(String jsonResponse) {
        if (jsonResponse == null || jsonResponse.isEmpty()) {
            return "User data is empty!";
        }

        try {
            ModShowResponseDto userInfo = objectMapper.readValue(jsonResponse, ModShowResponseDto.class);

            DateTimeFormatter formatter = dateFormater.withZone(ZoneId.systemDefault());

            String lastSeen = userInfo.lastSeen() != null
                    ? formatter.format(userInfo.lastSeen())
                    : "N/A";

            String createdAt = userInfo.createdAt() != null
                    ? formatter.format(userInfo.createdAt())
                    : "N/A";

            String updatedAt = userInfo.updatedAt() != null
                    ? formatter.format(userInfo.updatedAt())
                    : "N/A";

            return "\nID: " + userInfo.id() +
                    "\nEmail: " + userInfo.email() +
                    "\nNickname: " + userInfo.nickname() +
                    "\nDisplay name: " + userInfo.displayName() +
                    "\nBio: " + userInfo.bio() +
                    "\nAvatar: " + "ONLY IN GUI" +
                    "\nLast seen: " + lastSeen +
                    "\nCreated at: " + createdAt +
                    "\nUpdated at: " + updatedAt +
                    "\nVerified email: " + userInfo.isVerifiedEmail() +
                    "\nDeleted: " + userInfo.deleted() +
                    "\nBanned: " + userInfo.banned() +
                    "\n";

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
