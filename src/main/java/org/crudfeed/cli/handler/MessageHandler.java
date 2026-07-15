package org.crudfeed.cli.handler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.crudfeed.core.entity.Session;
import org.crudfeed.cli.network.ResponseClient;

import static java.lang.IO.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageHandler {

    @Getter
    private static final MessageHandler INSTANCE = new MessageHandler();

    private final Session session = Session.getINSTANCE();
    private final ResponseClient api = ResponseClient.getINSTANCE();

    public void writeMessage(String receiverId, String content) {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();
            String response = api.sendRequest("writeMessage|" + currentUserId + "|" + receiverId + "|" + content);

            println(response);
        } else {
            println("You are not logged in!");
        }
    }

    public String viewMessages(String receiverNickname) {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();
            return  api.sendRequest("viewMessages|" + currentUserId + "|" + receiverNickname);
        } else {
            return "You are not logged in!";
        }
    }

    public void editMessage(String messageId, String content) {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();
            String response = api.sendRequest("editMessage|" + currentUserId + "|" + messageId + "|" + content);

            println(response);
        } else {
            println("You are not logged in!");
        }
    }

    public void deleteMessage(String messageId) {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();
            String response = api.sendRequest("deleteMessage|" + currentUserId + "|" + messageId);

            println(response);
        } else {
            println("You are not logged in!");
        }
    }
}