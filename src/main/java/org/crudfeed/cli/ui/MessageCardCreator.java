package org.crudfeed.cli.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.crudfeed.cli.handler.AccountHandler;
import org.crudfeed.core.entity.Message;

import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageCardCreator {

    @Getter
    private static final MessageCardCreator INSTANCE = new MessageCardCreator();

    private final AccountHandler accountHandler = AccountHandler.getINSTANCE();
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String createMessageCard(String jsonResponse, long currentUserId) {
        if (jsonResponse == null || jsonResponse.isBlank() || jsonResponse.startsWith("Error")) {
            return "No messages or error: " + jsonResponse;
        }

        try {
            List<Message> messages = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

            if (messages.isEmpty()) {
                return "Messages list is empty.";
            }

            StringBuilder result = new StringBuilder();

            for (Message message : messages) {
                long senderId = message.getSenderId();
                long receiverId = message.getReceiverId();

                String sender = senderId == currentUserId
                        ? "YOU"
                        : "@" + accountHandler.getNicknameById(senderId);

                String receiver = receiverId == currentUserId
                        ? "YOU"
                        : "@" + accountHandler.getNicknameById(receiverId);

                String sentAt = message.getCreatedAt() != null
                        ? message.getCreatedAt().format(dateFormatter)
                        : "N/A";

                result.append("FROM: ").append(sender)
                        .append("\nTO: ").append(receiver)
                        .append("\nText: ").append(message.getText())
                        .append("\nDate: ").append(sentAt)
                        .append("\n\n");
            }

            return result.toString();
        } catch (Exception e) {
            return "Failed to parse messages: " + e.getMessage();
        }
    }
}