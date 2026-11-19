package org.crudfeed.cli.handler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.crudfeed.cli.session.Session;
import org.crudfeed.cli.network.ResponseClient;
import org.crudfeed.core.dto.*;

import static java.lang.IO.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageHandler {

    @Getter
    private static final MessageHandler INSTANCE = new MessageHandler();

    private final Session session = Session.getINSTANCE();
    private final ResponseClient api = ResponseClient.getINSTANCE();

    public void writeMessage(long receiverId, String content) {
        if (!session.isLoggedIn()) {
            println("You are not logged in!");
            return;
        }

        long currentUserId = session.getCurrentUser().getId();

        WriteMessageRequestDto dto = new WriteMessageRequestDto(currentUserId, receiverId, content);

        String response = api.sendPost("writeMessage", dto);
        println(response);
    }

    public String viewMessages(String receiverNickname) {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();

            ViewMessagesRequestDto dto = new ViewMessagesRequestDto(currentUserId, receiverNickname);

            return  api.sendPost("viewMessages", dto);
        } else {
            return "You are not logged in!";
        }
    }

    public void editMessage(long messageId, String content) {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();

            EditMessageRequestDto dto = new EditMessageRequestDto(currentUserId, messageId, content);

            String response = api.sendPost("editMessage", dto);

            println(response);
        } else {
            println("You are not logged in!");
        }
    }

    public void deleteMessage(long messageId) {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();

            DeleteMessageRequestDto dto = new DeleteMessageRequestDto(currentUserId, messageId);

            String response = api.sendPost("deleteMessage", dto);

            println(response);
        } else {
            println("You are not logged in!");
        }
    }
}