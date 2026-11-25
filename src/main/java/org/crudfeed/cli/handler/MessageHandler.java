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
        WriteMessageRequestDto dto = new WriteMessageRequestDto(receiverNickname, content);

        String response = api.sendPost("writeMessage", dto);
        println(response);
    }

    public String viewMessages(String receiverNickname) {
        ViewMessagesRequestDto dto = new ViewMessagesRequestDto(receiverNickname);

        return  api.sendPost("viewMessages", dto);
    }

    public void editMessage(long messageId, String content) {
        EditMessageRequestDto dto = new EditMessageRequestDto(messageId, content);

        String response = api.sendPost("editMessage", dto);

        println(response);
    }

    public void deleteMessage(long messageId) {
        DeleteMessageRequestDto dto = new DeleteMessageRequestDto(messageId);

        String response = api.sendPost("deleteMessage", dto);

        println(response);
    }
}