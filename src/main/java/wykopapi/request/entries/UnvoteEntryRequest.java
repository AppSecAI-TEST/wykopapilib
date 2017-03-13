package wykopapi.request.entries;

import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.dto.VoteEntry;
import wykopapi.request.AbstractRequest;
import wykopapi.request.ApiRequestBuilder;

import java.lang.reflect.Type;

public class UnvoteEntryRequest extends AbstractRequest<VoteEntry> {
    private final String userKey;
    private final int entryId;

    private UnvoteEntryRequest(String userKey, int entryId) {
        this.userKey = userKey;
        this.entryId = entryId;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("entries").addPathSegment("unvote")
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

    public static class Builder implements ApiRequestBuilder<UnvoteEntryRequest> {
        private String userKey;
        private int entryId;

        public Builder(String userKey, int entryId) {
            this.userKey = userKey;
            this.entryId = entryId;
        }

        @Override
        public UnvoteEntryRequest build() {
            return new UnvoteEntryRequest(userKey, entryId);
        }
    }
}
