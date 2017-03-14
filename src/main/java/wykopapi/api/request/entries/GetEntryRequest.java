package wykopapi.api.request.entries;

import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.api.dto.Entry;
import wykopapi.api.request.AbstractRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;

public final class GetEntryRequest extends AbstractRequest<Entry> {
    private final int entryId;
    private final boolean clearOutput;

    private GetEntryRequest(int entryId, boolean clearOutput) {
        this.entryId = entryId;
        this.clearOutput = clearOutput;
    }

    @Override
    public Request getRequest() {
        HttpUrl.Builder urlBuilder = newUrlBuilder()
                .addPathSegment("entries").addPathSegment("index")
                .addEncodedPathSegment(String.valueOf(entryId));
        if (clearOutput) {
            urlBuilder.addPathSegment("output").addPathSegment("clear");
        }

        return new Request.Builder()
                .url(urlBuilder.build()).get()
                .build();
    }

    @Override
    public Type getReturnType() {
        return Entry.class;
    }

    public static class Builder implements ApiRequestBuilder<GetEntryRequest> {
        private int entryId;
        private boolean clearOutput;

        public Builder(int entryId) {
            this.entryId = entryId;
        }

        public Builder setClearOutput(boolean clearOutput) {
            this.clearOutput = clearOutput;
            return this;
        }

        @Override
        public GetEntryRequest build() {
            return new GetEntryRequest(entryId, clearOutput);
        }
    }

}
