package wykopapi.api.request.stream;

import com.google.common.base.Strings;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import wykopapi.api.dto.Entry;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class StreamIndexRequest implements ApiRequest<List<Entry>> {
    private final String userKey;
    private final int page;
    private final boolean clearOutput;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("stream", "index")
                .addApiParam("page", String.valueOf(page))
                .addApiParam("userkey", userKey, !Strings.isNullOrEmpty(userKey))
                .addApiParam("output", "clear", clearOutput)
                .build();
    }

    @Override
    public Type getReturnType() {
        return new TypeToken<List<Entry>>(){}.getType();
    }

    public static StreamIndexRequestBuilder builder() {
        return new StreamIndexRequestBuilder();
    }

    public static class StreamIndexRequestBuilder {
        private int page;
        private String userKey;
        private boolean clearOutput;

        private StreamIndexRequestBuilder() {
            this.page = 1;
        }

        public StreamIndexRequestBuilder page(int page) {
            this.page = page > 0 ? page : 1;
            return this;
        }

        public StreamIndexRequestBuilder clearOutput(boolean clearOutput) {
            this.clearOutput = clearOutput;
            return this;
        }

        public StreamIndexRequestBuilder userKey(String userKey) {
            this.userKey = userKey;
            return this;
        }

        public StreamIndexRequest build() {
            return new StreamIndexRequest(userKey, page, clearOutput);
        }
    }
}
