package org.crudfeed.cli.handler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.crudfeed.cli.session.Session;
import org.crudfeed.cli.network.ResponseClient;
import org.crudfeed.core.dto.*;

import static java.lang.IO.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostHandler {

    @Getter
    private static final PostHandler INSTANCE = new PostHandler();

    private final Session session = Session.getINSTANCE();
    private final ResponseClient api = ResponseClient.getINSTANCE();

    public void createPost(String content) {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();

            CreatePostRequestDto dto = new CreatePostRequestDto(currentUserId, content);

            String response = api.sendPost("createPost", dto);

            println(response);
        } else {
            println("You are not logged in!");
        }
    }

    public void editPost(long postId, String content) {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();

            EditPostRequestDto dto = new EditPostRequestDto(currentUserId, postId, content);

            String response = api.sendPost("editPost", dto);

            println(response);
        } else {
            println("You are not logged in!");
        }
    }

    public void deletePost(long postId) {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();

            PostRequestDto dto = new PostRequestDto(currentUserId, postId);

            String response = api.sendPost("deletePost", dto);

            println(response);
        }  else {
            println("You are not logged in!");
        }
    }

    public String viewFeed() {
        return api.sendGet("viewFeed");
    }

    public String showUserPostsById() {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();

            AccountIdRequestDto dto = new AccountIdRequestDto(currentUserId);

            return api.sendPost("showUserPostsById", dto);
        }

        return "You are not logged in!";
    }

    public String viewUserLikes() {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();

            AccountIdRequestDto dto = new AccountIdRequestDto(currentUserId);

            return api.sendPost("viewUserLikes", dto);
        }
        return "You are not logged in!";
    }

    public String searchPostById(long postId) {
        PostIdRequestDto dto = new PostIdRequestDto(postId);

        return api.sendPost("searchPostById", dto);
    }

    public String searchPostByWords(String content) {
        PostContentRequestDto dto = new PostContentRequestDto(content);

        return api.sendPost("searchPostByWords", dto);
    }

    public void likePost(long postId) {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();

            PostRequestDto dto = new PostRequestDto(currentUserId, postId);

            String response = api.sendPost("likePost", dto);

            println(response);
        } else {
            println("You are not logged in!");
        }
    }

    public void dislikePost(long postId) {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();

            PostRequestDto dto = new PostRequestDto(currentUserId, postId);

            String response = api.sendPost("dislikePost", dto);

            println(response);
        } else {
            println("You are not logged in!");
        }
    }
}