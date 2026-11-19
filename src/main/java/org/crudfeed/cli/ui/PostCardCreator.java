package org.crudfeed.cli.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.crudfeed.core.entity.Post;

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

        try {
            List<Post> posts = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

            if (posts.isEmpty()) {
                return "Feed is empty.";
            }

            StringBuilder result = new StringBuilder();

            for (Post post : posts) {
                String createdAt = post.getCreatedAt() != null ? post.getCreatedAt().format(dateFormatter) : "N/A";
                String updatedAt = post.getUpdatedAt() != null ? post.getUpdatedAt().format(dateFormatter) : "N/A";

                result.append("ID: ").append(post.getId())
                        .append("\nAuthor (ID): ").append(post.getUserId())
                        .append("\nText: ").append(post.getContent())
                        .append("\nLikes: IN REWORK")
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