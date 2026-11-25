package org.crudfeed.cli.ui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.crudfeed.core.dto.account.ShowFriendsListRequestDto;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FriendsListCardCreator {
    @Getter
    public static final FriendsListCardCreator INSTANCE = new FriendsListCardCreator();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String createFriendListCard(String jsonResponse) {
        if(jsonResponse == null || jsonResponse.isEmpty()) {
            return "No friends or error: " + jsonResponse;
        }

        try {
            List<ShowFriendsListRequestDto> friends = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

            if (friends.isEmpty()) {
                return "Friends list is empty.";
            }

            StringBuilder result = new StringBuilder();

            for (ShowFriendsListRequestDto friend : friends) {
                result.append("\nFriend ID: ").append(friend.friendId())
                        .append("\nFriend Name: ").append(friend.friendName())
                        .append("\nConfirmed: IN THE FUTURE")
                        .append("\n");
            }

            return result.toString();
        } catch (Exception e) {
            return "Failed to create friends list: " + jsonResponse;
        }
    }
}
