package wykopapi.request.entries;

import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.dto.EntryOperation;
import wykopapi.request.AbstractRequest;
import wykopapi.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.Collections;

public final class EditEntryRequest extends AbstractRequest<EntryOperation> {
    private final String userKey;
    private final String body;
    private final int entryId;

    private EditEntryRequest(String userKey, int entryId, String body) {
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
        return EntryOperation.class;
    }

    public static class Builder implements ApiRequestBuilder<EditEntryRequest> {
        private int entryId;
        private String userKey;
        private String body;

        public Builder(String userKey, int entryId, String body) {
            this.userKey = userKey;
            this.entryId = entryId;
            this.body = body;
        }

        @Override
        public EditEntryRequest build() {
            return new EditEntryRequest(userKey, entryId, body);
        }
    }
}
