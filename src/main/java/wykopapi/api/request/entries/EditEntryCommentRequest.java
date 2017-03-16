package wykopapi.api.request.entries;

import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.api.dto.IdResult;
import wykopapi.api.request.AbstractRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.Collections;

// TODO check if its possible to change embed url/file
public final class EditEntryCommentRequest extends AbstractRequest<IdResult> {
    private final String userKey;
    private final int entryId;
    private final int commentId;
    private final String body;

    private EditEntryCommentRequest(String userKey, int entryId, int commentId, String body) {
        this.userKey = userKey;
        this.entryId = entryId;
        this.commentId = commentId;
        this.body = body;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("entries").addPathSegment("editcomment")
                .addEncodedPathSegment(String.valueOf(entryId)).addEncodedPathSegment(String.valueOf(commentId))
                .addPathSegment("userkey").addPathSegment(userKey)
                .build();

        return new Request.Builder()
                .url(url).post(createBodyFromParams(Collections.singletonMap("body", body)))
                .build();
    }

    @Override
    public Type getReturnType() {
        return IdResult.class;
    }

    public static class Builder implements ApiRequestBuilder<EditEntryCommentRequest> {
        private String userKey;
        private int entryId;
        private int commentId;
        private String body;

        public Builder(String userKey, int entryId, int commentId, String body) {
            this.userKey = userKey;
            this.entryId = entryId;
            this.commentId = commentId;
            this.body = body;
        }

        @Override
        public EditEntryCommentRequest build() {
            return new EditEntryCommentRequest(userKey, entryId, commentId, body);
        }
    }
}
