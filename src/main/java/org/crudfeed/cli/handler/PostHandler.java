package org.crudfeed.cli.handler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.crudfeed.core.entity.Session;
import org.crudfeed.cli.network.ResponseClient;

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
            String response = api.sendRequest("createPost|" + currentUserId + "|" + content);

            println(response);
        } else {
            println("You are not logged in!");
        }
    }

    public void editPost(String postId, String content) {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();
            String response = api.sendRequest("editPost|" + postId + "|" + content + "|" + currentUserId);

            println(response);
        } else {
            println("You are not logged in!");
        }
    }

    public void deletePost(String postId) {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();
            String response = api.sendRequest("deletePost|" + postId + "|" + currentUserId);

            println(response);
        }  else {
            println("You are not logged in!");
        }
    }

    public String viewFeed() {
        return api.sendRequest("viewFeed");
    }

    public String showUserPostsById() {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();

            return api.sendRequest(
                    "showUserPostsById|" + currentUserId
            );
        }

        return "You are not logged in!";
    }

    public String viewUserLikes() {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();
            return api.sendRequest("viewUserLikes|" + currentUserId);
        }
        return "You are not logged in!";
    }

    public String searchPostById(String postId) {
        return api.sendRequest("searchPostById|" + postId);
    }

    public String searchPostByWords(String content) { return api.sendRequest("searchPostByWords|" + content); }

    public void likePost(String postId) {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();
            String response = api.sendRequest("likePost|" + postId + "|" + currentUserId);

            println(response);
        } else {
            println("You are not logged in!");
        }
    }

    public void disLikePost(String postId) {
        if (session.isLoggedIn()) {
            long currentUserId = session.getCurrentUser().getId();
            String response = api.sendRequest("disLikePost|" + postId + "|" + currentUserId);

            println(response);
        } else {
            println("You are not logged in!");
        }
    }
}