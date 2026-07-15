package org.crudfeed.cli.ui;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.crudfeed.cli.handler.AccountHandler;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageCardCreator {

    @Getter
    private static final MessageCardCreator INSTANCE = new MessageCardCreator();

    private final AccountHandler accountHandler = AccountHandler.getINSTANCE();

    public String createMessageCard(String response, long currentUserId) {

        StringBuilder result = new StringBuilder();

        for (String line : response.split("\n")) {

            if (line.isBlank()) {
                continue;
            }

            String[] message = line.split("\\|", -1);

            long senderId = Long.parseLong(message[1]);
            long receiverId = Long.parseLong(message[2]);

            boolean isOwnMessage = senderId == currentUserId;

            String senderNickname = accountHandler.getNicknameById(String.valueOf(senderId));

            String receiverNickname = accountHandler.getNicknameById(String.valueOf(receiverId));

            String sender = isOwnMessage
                    ? "YOU"
                    : "@" + senderNickname;

            String receiver = receiverId == currentUserId
                    ? "YOU"
                    : "@" + receiverNickname;

            result.append("FROM: ")
                    .append(sender)
                    .append("\nTO: ")
                    .append(receiver)
                    .append("\nText: ")
                    .append(message[3])
                    .append("\nDate: ")
                    .append(message[4].substring(0, 19))
                    .append("\n\n");
        }

        return result.toString();
    }
}