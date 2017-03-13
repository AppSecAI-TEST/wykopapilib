package wykopapi.request.entries;

import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.dto.AddEntry;
import wykopapi.request.AbstractRequest;
import wykopapi.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.Collections;

public class EntriesEditRequest extends AbstractRequest<AddEntry> {
    private final String userKey;
    private final String body;
    private final int entryId;

    private EntriesEditRequest(String userKey, int entryId, String body) {
        this.userKey = userKey;
        this.body = body;
        this.entryId = entryId;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("entries").addPathSegment("edit")
                .addEncodedPathSegment(String.valueOf(entryId))
                .addPathSegment("userkey").addEncodedPathSegment(userKey)
                .build();

        return new Request.Builder()
                .url(url).post(createBodyFromParams(Collections.singletonMap("body", body)))
                .build();
    }

    @Override
    public Type getReturnType() {
        return AddEntry.class;
    }

    public static class Builder implements ApiRequestBuilder<EntriesEditRequest> {
        private int entryId;
        private String userKey;
        private String body;

        public Builder(String userKey, int entryId, String body) {
            this.userKey = userKey;
            this.entryId = entryId;
            this.body = body;
        }

        @Override
        public EntriesEditRequest build() {
            return new EntriesEditRequest(userKey, entryId, body);
        }
    }
}
