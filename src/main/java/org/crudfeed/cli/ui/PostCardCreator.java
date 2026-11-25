package org.crudfeed.cli.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.crudfeed.core.dto.post.ViewPostRequestDto;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCardCreator {

    @Getter
    private static final PostCardCreator INSTANCE = new PostCardCreator();
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String createPostCard(String jsonResponse) {
        if (jsonResponse == null || jsonResponse.isBlank() || jsonResponse.startsWith("Error")) {
            return "No posts or error: " + jsonResponse;
        }

        String trimmed = jsonResponse.trim();

        try {
            List<ViewPostRequestDto> posts;
            if (trimmed.startsWith("[")) {
                posts = objectMapper.readValue(trimmed, new TypeReference<>() {});
            } else if (trimmed.startsWith("{")) {
                posts = List.of(objectMapper.readValue(trimmed, ViewPostRequestDto.class));
            } else {
                return "Failed to parse posts: unexpected JSON format";
            }

            if (posts.isEmpty()) {
                return "Feed is empty.";
            }

            DateTimeFormatter formatter = dateFormatter.withZone(ZoneId.systemDefault());
            StringBuilder result = new StringBuilder();

            for (ViewPostRequestDto post : posts) {
                String createdAt = post.createdAt() != null
                        ? formatter.format(post.createdAt())
                        : "N/A";

                String updatedAt = post.updatedAt() != null
                        ? formatter.format(post.updatedAt())
                        : "N/A";

                result.append("\nPost ID: ").append(post.id())
                        .append("\nAuthor (ID): ").append(post.authorId())
                        .append("\nAuthor (NAME): ").append(post.authorName())
                        .append("\nText: ").append(post.content())
                        .append("\nLikes: ").append(post.likes())
                        .append("\nDislikes: ").append(post.dislikes())
                        .append("\nCreated at: ").append(createdAt)
                        .append("\nUpdated at: ").append(updatedAt)
                        .append("\n");
            }
            return result.toString();
        } catch (Exception e) {
            return "Failed to parse posts: " + e.getMessage();
        }
    }
}