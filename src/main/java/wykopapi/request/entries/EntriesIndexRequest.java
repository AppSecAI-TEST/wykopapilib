package wykopapi.request.entries;

import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.dto.Entry;
import wykopapi.request.AbstractRequest;
import wykopapi.request.ApiRequestBuilder;

import java.lang.reflect.Type;

public class EntriesIndexRequest extends AbstractRequest<Entry> {
    private final int entryId;

    private EntriesIndexRequest(int entryId) {
        this.entryId = entryId;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("entries").addPathSegment("index")
                .addEncodedPathSegment(String.valueOf(entryId))
                .build();

        return new Request.Builder()
                .url(url).get()
                .build();
    }

    @Override
    public Type getReturnType() {
        return Entry.class;
    }

    public static class Builder implements ApiRequestBuilder<EntriesIndexRequest> {
        private int entryId;

        public Builder(int entryId) {
            this.entryId = entryId;
        }

        @Override
        public EntriesIndexRequest build() {
            return new EntriesIndexRequest(entryId);
        }
    }

}
