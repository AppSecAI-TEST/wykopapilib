package wykopapi.request.entries;

import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.dto.VoteEntry;
import wykopapi.request.AbstractRequest;
import wykopapi.request.ApiRequestBuilder;

import java.lang.reflect.Type;

public class VoteEntryRequest extends AbstractRequest<VoteEntry> {
    private final String userKey;
    private final int entryId;

    private VoteEntryRequest(String userKey, int entryId) {
        this.userKey = userKey;
        this.entryId = entryId;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("entries").addPathSegment("vote")
                .addPathSegment("entry").addEncodedPathSegment(String.valueOf(entryId))
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

    public static class Builder implements ApiRequestBuilder<VoteEntryRequest> {
        private String userKey;
        private int entryId;

        public Builder(String userKey, int entryId) {
            this.userKey = userKey;
            this.entryId = entryId;
        }

        @Override
        public VoteEntryRequest build() {
            return new VoteEntryRequest(userKey, entryId);
        }
    }
}
