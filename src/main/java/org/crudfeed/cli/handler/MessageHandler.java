package org.crudfeed.cli.handler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.crudfeed.cli.session.Session;
import org.crudfeed.cli.network.ResponseClient;
import org.crudfeed.core.dto.message.ViewMessagesRequestDto;
import org.crudfeed.core.dto.message.DeleteMessageRequestDto;
import org.crudfeed.core.dto.message.EditMessageRequestDto;
import org.crudfeed.core.dto.message.WriteMessageRequestDto;

import static java.lang.IO.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageHandler {

    @Getter
    private static final MessageHandler INSTANCE = new MessageHandler();

    private final Session session = Session.getINSTANCE();
    private final ResponseClient api = ResponseClient.getINSTANCE();

    public void writeMessage(String receiverNickname, String content) {
        long currentUserId = session.getCurrentUser().getId();

        WriteMessageRequestDto dto = new WriteMessageRequestDto(currentUserId, receiverNickname, content);

        String response = api.sendPost("writeMessage", dto);
        println(response);
    }

    public String viewMessages(String receiverNickname) {
        long currentUserId = session.getCurrentUser().getId();

        ViewMessagesRequestDto dto = new ViewMessagesRequestDto(currentUserId, receiverNickname);

        return  api.sendPost("viewMessages", dto);
    }

    public void editMessage(long messageId, String content) {
        long currentUserId = session.getCurrentUser().getId();

        EditMessageRequestDto dto = new EditMessageRequestDto(currentUserId, messageId, content);

        String response = api.sendPost("editMessage", dto);

        println(response);
    }

    public void deleteMessage(long messageId) {
        long currentUserId = session.getCurrentUser().getId();

        DeleteMessageRequestDto dto = new DeleteMessageRequestDto(currentUserId, messageId);

        String response = api.sendPost("deleteMessage", dto);

        println(response);
    }
}