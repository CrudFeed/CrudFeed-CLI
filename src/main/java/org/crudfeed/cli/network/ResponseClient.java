package org.crudfeed.cli.network;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.crudfeed.core.entity.User;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseClient {

    @Getter
    private static final ResponseClient INSTANCE = new ResponseClient();

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 7777;

    public String sendRequest(String command) {
        SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

        try (SSLSocket socket = (SSLSocket) socketFactory.createSocket(SERVER_HOST, SERVER_PORT)) {
            socket.setSoTimeout(5000);

            try (DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                 DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {

                outputStream.writeUTF(command);
                outputStream.flush();

                return inputStream.readUTF();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Could not connect to secured server!";
        }
    }

    public User parseUser(String dataFromServer) {
        String[] parts = dataFromServer.split("\\|");

        if (parts.length < 4) {
            return null;
        }

        String nickname = parts[1];
        String password = parts[2];
        long userId = Long.parseLong(parts[3]);

        return new User(nickname, password, userId);
    }
}