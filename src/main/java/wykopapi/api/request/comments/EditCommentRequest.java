package wykopapi.api.request.comments;

import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.api.dto.IdResult;
import wykopapi.api.request.AbstractRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.Collections;

// TODO check if its possible to change embed url/file
public final class EditCommentRequest extends AbstractRequest<IdResult> {
    private final String userKey;
    private final int commentId;
    private final String body;

    private EditCommentRequest(String userKey, int commentId, String body) {
        this.userKey = userKey;
        this.commentId = commentId;
        this.body = body;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("comments").addPathSegment("edit")
                .addEncodedPathSegment(String.valueOf(commentId))
                .addPathSegment("userkey").addEncodedPathSegment(userKey)
                .build();

        return new Request.Builder()
                .url(url).post(createBodyFromParams(Collections.singletonMap("body", body)))
                .build();
    }

    @Override
    public Type getReturnType() {
        return IdResult.class;
    }

    public static class Builder implements ApiRequestBuilder<EditCommentRequest> {
        private String userKey;
        private int commentId;
        private String body;

        public Builder(String userKey, int commentId, String body) {
            this.userKey = userKey;
            this.commentId = commentId;
            this.body = body;
        }

        @Override
        public EditCommentRequest build() {
            return new EditCommentRequest(userKey, commentId, body);
        }
    }
}
