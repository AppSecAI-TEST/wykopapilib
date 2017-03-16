package wykopapi.api.request.comments;

import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.api.request.AbstractRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;

// TODO proper return type
public final class PlusCommentRequest extends AbstractRequest<String> {
    private final String userKey;
    private final int linkId;
    private final int commentId;

    private PlusCommentRequest(String userKey, int linkId, int commentId) {
        this.userKey = userKey;
        this.linkId = linkId;
        this.commentId = commentId;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("comments").addPathSegment("plus")
                .addEncodedPathSegment(String.valueOf(linkId))
                .addEncodedPathSegment(String.valueOf(commentId))
                .addPathSegment("userkey").addEncodedPathSegment(userKey)
                .build();

        return new Request.Builder()
                .url(url).get()
                .build();
    }

    @Override
    public Type getReturnType() {
        return String.class;
    }

    public static class Builder implements ApiRequestBuilder<PlusCommentRequest> {
        private String userKey;
        private int linkId;
        private int commentId;

        public Builder(String userKey, int linkId, int commentId) {
            this.userKey = userKey;
            this.linkId = linkId;
            this.commentId = commentId;
        }

        @Override
        public PlusCommentRequest build() {
            return new PlusCommentRequest(userKey, linkId, commentId);
        }
    }
}
