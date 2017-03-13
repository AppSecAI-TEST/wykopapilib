package wykopapi.request.entries;

import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.dto.VoteEntry;
import wykopapi.request.AbstractRequest;
import wykopapi.request.ApiRequestBuilder;

import java.lang.reflect.Type;

public final class UnvoteEntryCommentRequest extends AbstractRequest<VoteEntry> {
    private final String userKey;
    private final int entryId;
    private final int commentId;

    private UnvoteEntryCommentRequest(String userKey, int entryId, int commentId) {
        this.userKey = userKey;
        this.entryId = entryId;
        this.commentId = commentId;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("entries").addPathSegment("unvote")
                .addPathSegment("comment")
                .addEncodedPathSegment(String.valueOf(entryId)).addEncodedPathSegment(String.valueOf(commentId))
                .addPathSegment("userkey").addEncodedPathSegment(userKey)
                .build();

        return new Request.Builder()
                .url(url).get()
                .build();
    }

    @Override
    public Type getReturnType() {
        return VoteEntry.class;
    }

    public static class Builder implements ApiRequestBuilder<UnvoteEntryCommentRequest> {
        private String userKey;
        private int entryId;
        private int commentId;

        public Builder(String userKey, int entryId, int commentId) {
            this.userKey = userKey;
            this.entryId = entryId;
            this.commentId = commentId;
        }

        @Override
        public UnvoteEntryCommentRequest build() {
            return new UnvoteEntryCommentRequest(userKey, entryId, commentId);
        }
    }
}
