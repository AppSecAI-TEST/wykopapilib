package wykopapi.request.entries;

import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.dto.EntryOperation;
import wykopapi.request.AbstractRequest;
import wykopapi.request.ApiRequestBuilder;

import java.lang.reflect.Type;

public final class DeleteEntryCommentRequest extends AbstractRequest<EntryOperation> {
    private final String userKey;
    private final int entryId;
    private final int commentId;

    private DeleteEntryCommentRequest(String userKey, int entryId, int commentId) {
        this.userKey = userKey;
        this.entryId = entryId;
        this.commentId = commentId;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("entries").addPathSegment("deletecomment")
                .addEncodedPathSegment(String.valueOf(entryId)).addEncodedPathSegment(String.valueOf(commentId))
                .addPathSegment("userkey").addEncodedPathSegment(userKey)
                .build();

        return new Request.Builder()
                .url(url).get()
                .build();
    }

    @Override
    public Type getReturnType() {
        return EntryOperation.class;
    }

    public static class Builder implements ApiRequestBuilder<DeleteEntryCommentRequest> {
        private String userKey;
        private int entryId;
        private int commentId;

        public Builder(String userKey, int entryId, int commentId) {
            this.userKey = userKey;
            this.entryId = entryId;
            this.commentId = commentId;
        }

        @Override
        public DeleteEntryCommentRequest build() {
            return new DeleteEntryCommentRequest(userKey, entryId, commentId);
        }
    }
}
