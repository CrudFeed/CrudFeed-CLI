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
        CreatePostRequestDto dto = new CreatePostRequestDto(session.getCurrentUser().getId(), content);

        String response = api.sendPost("createPost", dto);

        println(response);

    }

    public void editPost(long postId, String content) {
        EditPostRequestDto dto = new EditPostRequestDto(session.getCurrentUser().getId(), postId, content);

        String response = api.sendPost("editPost", dto);

        println(response);
    }

    public void deletePost(long postId) {
        PostRequestDto dto = new PostRequestDto(session.getCurrentUser().getId(), postId);

        String response = api.sendPost("deletePost", dto);

        println(response);
    }

    public String viewFeed() {
        return api.sendGet("viewFeed");
    }

    public String showUserPostsById() {
        AccountIdRequestDto dto = new AccountIdRequestDto(session.getCurrentUser().getId());

        return api.sendPost("showUserPostsById", dto);
    }

    public String viewUserLikes() {
        AccountIdRequestDto dto = new AccountIdRequestDto(session.getCurrentUser().getId());

        return api.sendPost("viewUserLikes", dto);
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
        PostRequestDto dto = new PostRequestDto(session.getCurrentUser().getId(), postId);

        String response = api.sendPost("likePost", dto);

        println(response);
    }

    public void dislikePost(long postId) {
        PostRequestDto dto = new PostRequestDto(session.getCurrentUser().getId(), postId);

        String response = api.sendPost("dislikePost", dto);

        println(response);
    }
}