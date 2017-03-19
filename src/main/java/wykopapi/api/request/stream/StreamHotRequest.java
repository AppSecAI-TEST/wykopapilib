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
public final class StreamHotRequest implements ApiRequest<List<Entry>> {
    private final String userKey;
    private final int page;
    private final int period;
    private final boolean clearOutput;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("stream", "hot")
                .addApiParam("page", String.valueOf(page))
                .addApiParam("period", String.valueOf(period))
                .addApiParam("userkey", userKey, !Strings.isNullOrEmpty(userKey))
                .addApiParam("output", "clear", clearOutput)
                .build();
    }

    @Override
    public Type getReturnType() {
        return new TypeToken<List<Entry>>(){}.getType();
    }

    public static StreamHotRequestBuilder builder() {
        return new StreamHotRequestBuilder();
    }

    public static class StreamHotRequestBuilder {
        private String userKey;
        private int page;
        private int period;
        private boolean clearOutput;

        private StreamHotRequestBuilder() {
            this.page = 1;
            this.period = 12;
        }

        public StreamHotRequestBuilder userKey(String userKey) {
            this.userKey = userKey;
            return this;
        }

        public StreamHotRequestBuilder page(int page) {
            this.page = page > 0 ? page : 1;
            return this;
        }

        public StreamHotRequestBuilder period(int period) {
            this.period = period > 0 ? period : 1;
            return this;
        }

        public StreamHotRequestBuilder clearOutput(boolean clearOutput) {
            this.clearOutput = clearOutput;
            return this;
        }

        public StreamHotRequest build() {
            return new StreamHotRequest(userKey, page, period, clearOutput);
        }
    }
}
