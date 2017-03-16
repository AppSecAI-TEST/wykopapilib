package wykopapi.api.request.comments;

import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.api.dto.IdResult;
import wykopapi.api.request.AbstractRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;

public class DeleteCommentRequest extends AbstractRequest<IdResult> {
    private final String userKey;
    private final int commentId;

    private DeleteCommentRequest(String userKey, int commentId) {
        this.userKey = userKey;
        this.commentId = commentId;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("comments").addPathSegment("delete")
                .addEncodedPathSegment(String.valueOf(commentId))
                .addPathSegment("userkey").addEncodedPathSegment(userKey)
                .build();

        return new Request.Builder()
                .url(url).get()
                .build();
    }

    @Override
    public Type getReturnType() {
        return IdResult.class;
    }

    public static class Builder implements ApiRequestBuilder<DeleteCommentRequest> {
        private String userKey;
        private int commentId;

        public Builder(String userKey, int commentId) {
            this.userKey = userKey;
            this.commentId = commentId;
        }

        @Override
        public DeleteCommentRequest build() {
            return new DeleteCommentRequest(userKey, commentId);
        }
    }
}
