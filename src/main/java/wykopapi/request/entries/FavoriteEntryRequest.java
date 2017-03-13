package wykopapi.request.entries;

import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.request.AbstractRequest;
import wykopapi.request.ApiRequestBuilder;

import java.lang.reflect.Type;

public class FavoriteEntryRequest extends AbstractRequest<Boolean> {
    private final String userKey;
    private final int entryId;

    private FavoriteEntryRequest(String userKey, int entryId) {
        this.userKey = userKey;
        this.entryId = entryId;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("entries").addPathSegment("favorite")
                .addEncodedPathSegment(String.valueOf(entryId))
                .addPathSegment("userkey").addEncodedPathSegment(userKey)
                .build();

        return new Request.Builder()
                .url(url).get()
                .build();
    }

    @Override
    public Type getReturnType() {
        return Boolean.class;
    }

    public static class Builder implements ApiRequestBuilder<FavoriteEntryRequest> {
        private String userKey;
        private int entryId;

        public Builder(String userKey, int entryId) {
            this.userKey = userKey;
            this.entryId = entryId;
        }

        @Override
        public FavoriteEntryRequest build() {
            return new FavoriteEntryRequest(userKey, entryId);
        }
    }
}
