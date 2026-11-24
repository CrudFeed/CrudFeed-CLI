package org.crudfeed.cli.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.crudfeed.core.dto.ViewFeedRequestDto;

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

        try {
            List<ViewFeedRequestDto> posts = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

            if (posts.isEmpty()) {
                return "Feed is empty.";
            }

            StringBuilder result = new StringBuilder();

            for (ViewFeedRequestDto post : posts) {
                DateTimeFormatter formatter = dateFormatter.withZone(ZoneId.systemDefault());

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