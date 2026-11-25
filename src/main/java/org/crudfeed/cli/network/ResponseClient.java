package org.crudfeed.cli.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.crudfeed.core.dto.account.AuthResultResponseDto;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseClient {

    @Getter
    private static final ResponseClient INSTANCE = new ResponseClient();

    @Getter
    @Setter
    private static String baseUrl = "http://100.65.191.8:8080/api";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public String sendGet(String endpoint) {
        try {
            String url = baseUrl + "/" + endpoint;
            return executeGet(url);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public <T> String sendPost(String endpoint, T requestDto) {
        try {
            String jsonPayload = objectMapper.writeValueAsString(requestDto);

            String url = baseUrl + "/" + endpoint;

            return executePost(url, jsonPayload);

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private String executeGet(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return "Error: Server returned code " + response.statusCode();
        }

        return response.body();
    }

    private String executePost(String url, String jsonBody) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return "Error: Server returned code " + response.statusCode();
        }

        return response.body();
    }

    public AuthResultResponseDto parseUser(String jsonResponse) {
        if (jsonResponse == null || jsonResponse.isBlank()) {
            return null;
        }
        String trimmed = jsonResponse.trim();
        if (!trimmed.startsWith("{")) {
            return null;
        }
        try {
            return objectMapper.readValue(trimmed, AuthResultResponseDto.class);
        } catch (Exception e) {
            System.err.println("JSON parse error: " + e.getMessage());
            return null;
        }
    }
}