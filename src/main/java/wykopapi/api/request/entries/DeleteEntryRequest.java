package wykopapi.api.request.entries;

import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.api.dto.EntryOperation;
import wykopapi.api.request.AbstractRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;

public final class DeleteEntryRequest extends AbstractRequest<EntryOperation> {
    private final String userKey;
    private final int entryId;

    private DeleteEntryRequest(String userKey, int entryId) {
        this.userKey = userKey;
        this.entryId = entryId;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("entries").addPathSegment("delete")
                .addEncodedPathSegment(String.valueOf(entryId))
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

    public static class Builder implements ApiRequestBuilder<DeleteEntryRequest> {
        private String userKey;
        private int entryId;

        public Builder(String userKey, int entryId) {
            this.userKey = userKey;
            this.entryId = entryId;
        }

        @Override
        public DeleteEntryRequest build() {
            return new DeleteEntryRequest(userKey, entryId);
        }
    }
}
