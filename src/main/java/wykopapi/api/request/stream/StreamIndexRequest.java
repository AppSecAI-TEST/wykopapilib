package wykopapi.api.request.stream;

import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.api.request.AbstractRequest;
import wykopapi.api.request.ApiRequestBuilder;
import wykopapi.api.dto.Entry;

import java.lang.reflect.Type;
import java.util.List;

public final class StreamIndexRequest extends AbstractRequest<List<Entry>> {
    private final int page;
    private final boolean clearOutput;

    private StreamIndexRequest(int page, boolean clearOutput) {
        this.page = page;
        this.clearOutput = clearOutput;
    }

    @Override
    public Request getRequest() {
        HttpUrl.Builder urlBuilder = newUrlBuilder()
                .addPathSegment("stream").addPathSegment("index")
                .addPathSegment("page").addEncodedPathSegment(String.valueOf(page));
        if (clearOutput) urlBuilder.addPathSegment("output").addPathSegment("clear");

        return new Request.Builder()
                .url(urlBuilder.build()).get()
                .build();
    }

    @Override
    public Type getReturnType() {
        return new TypeToken<List<Entry>>(){}.getType();
    }

    public static class Builder implements ApiRequestBuilder<StreamIndexRequest> {
        private int page;
        private boolean clearOutput;

        public Builder() {
            this.page = 1;
        }

        public Builder setPage(int page) {
            this.page = page > 0 ? page : 1;
            return this;
        }

        public Builder setClearOutput(boolean clearOutput) {
            this.clearOutput = clearOutput;
            return this;
        }

        @Override
        public StreamIndexRequest build() {
            return new StreamIndexRequest(page, clearOutput);
        }
    }
}
