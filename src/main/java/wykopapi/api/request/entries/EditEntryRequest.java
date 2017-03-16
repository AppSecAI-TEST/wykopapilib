package wykopapi.api.request.entries;

import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.api.dto.IdResult;
import wykopapi.api.request.AbstractRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.Collections;

// TODO check if its possible to change embed url/file
public final class EditEntryRequest extends AbstractRequest<IdResult> {
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
        return IdResult.class;
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
