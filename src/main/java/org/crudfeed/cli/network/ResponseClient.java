package org.crudfeed.cli.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.crudfeed.cli.session.Session;
import org.crudfeed.core.dto.account.AuthResultResponseDto;
import org.crudfeed.core.dto.account.UserResponseDto;
import org.crudfeed.core.entity.User;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseClient {

    @Getter
    private static final ResponseClient INSTANCE = new ResponseClient();

    @Getter
    private static final String TAILSCALE = "http://100.65.191.8:8080/api" ;

    @Getter
    private static final String LOCALHOST = "http://localhost:8080/api" ;

    @Getter
    @Setter
    private static String baseUrl = "http://100.65.191.8:8080/api";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Getter
    private String lastToken;

    public String sendGet(String endpoint) {
        try {
            return executeGet(baseUrl + "/" + endpoint);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public <T> String sendPost(String endpoint, T requestDto) {
        try {
            String jsonPayload = objectMapper.writeValueAsString(requestDto);
            return executePost(baseUrl + "/" + endpoint, jsonPayload);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private String executeGet(String url) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET();

        attachAuthHeader(builder);

        HttpResponse<String> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        captureToken(response);

        if (response.statusCode() != 200) {
            return "Error: Server returned code " + response.statusCode();
        }

        return response.body();
    }

    private String executePost(String url, String jsonBody) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody));

        attachAuthHeader(builder);

        HttpResponse<String> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        captureToken(response);

        if (response.statusCode() != 200) {
            return "Error: Server returned code " + response.statusCode();
        }

        return response.body();
    }

    private void attachAuthHeader(HttpRequest.Builder builder) {
        String token = Session.getINSTANCE().getToken();
        if (token != null && !token.isBlank()) {
            builder.header("Authorization", "Bearer " + token);
        }
    }

    private void captureToken(HttpResponse<String> response) {
        String header = response.headers().firstValue("Authorization").orElse(null);
        if (header == null || header.isBlank()) {
            this.lastToken = null;
            return;
        }
        String trimmed = header.trim();
        this.lastToken = trimmed.startsWith("Bearer ") ? trimmed.substring(7).trim() : trimmed;
    }

    public AuthResultResponseDto parseUser(String jsonResponse) {
        return parseUser(jsonResponse, this.lastToken);
    }

    public AuthResultResponseDto parseUser(String jsonResponse, String token) {
        if (jsonResponse == null || jsonResponse.isBlank()) {
            return null;
        }

        String trimmed = jsonResponse.trim();
        if (!trimmed.startsWith("{")) {
            return null;
        }

        try {
            AuthResultResponseDto result = objectMapper.readValue(trimmed, AuthResultResponseDto.class);
            if (result != null && result.ok() && result.user() != null) {
                String resolvedToken = (token != null && !token.isBlank()) ? token : this.lastToken;
                if (resolvedToken != null && !resolvedToken.isBlank()) {
                    Session.getINSTANCE().login(toUser(result.user()), resolvedToken);
                }
            }
            return result;
        } catch (Exception e) {
            System.err.println("JSON parse error: " + e.getMessage());
            return null;
        }
    }

    private User toUser(UserResponseDto dto) {
        User user = new User();
        user.setId(dto.id());
        user.setEmail(dto.email());
        user.setNickname(dto.nickname());
        user.setDisplayName(dto.displayName());
        user.setRole(dto.role());
        return user;
    }
}