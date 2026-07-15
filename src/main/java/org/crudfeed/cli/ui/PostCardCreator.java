package org.crudfeed.cli.ui;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCardCreator {

    @Getter
    private static final PostCardCreator INSTANCE = new PostCardCreator();


    public String createPostCard(String response) {

        StringBuilder result = new StringBuilder();

        for (String line : response.split("\n")) {

            if (line.isBlank()) {
                continue;
            }

            String[] post = line.split("\\|", -1);

            result.append("ID: ")
                    .append(post[0])
                    .append("\nAuthor: ")
                    .append(post[1])
                    .append("\nText: ")
                    .append(post[2])
                    .append("\nLikes: ")
                    .append(post[3])
                    .append("\n\n");
        }

        return result.toString();
    }
}