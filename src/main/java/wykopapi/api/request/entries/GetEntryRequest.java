package wykopapi.api.request.entries;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import wykopapi.api.dto.Entry;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class GetEntryRequest implements ApiRequest<Entry> {
    private final int entryId;
    private final boolean clearOutput;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("entries", "index")
                .addMethodParam(String.valueOf(entryId))
                .addApiParam("output", "clear", clearOutput)
                .build();
    }

    @Override
    public Type getReturnType() {
        return Entry.class;
    }

    public static GetEntryRequestBuilder builder(int entryId) {
        return new GetEntryRequestBuilder(entryId);
    }

    public static class GetEntryRequestBuilder {
        private int entryId;
        private boolean clearOutput;

        private GetEntryRequestBuilder(int entryId) {
            if (entryId < 0) {
                throw new IllegalArgumentException("Parameter cannot be negative");
            }
            this.entryId = entryId;
        }

        public GetEntryRequestBuilder clearOutput(boolean clearOutput) {
            this.clearOutput = clearOutput;
            return this;
        }

        public GetEntryRequest build() {
            return new GetEntryRequest(entryId, clearOutput);
        }
    }

}
