package wykopapi.request.stream;

import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.request.AbstractRequest;
import wykopapi.request.ApiRequestBuilder;
import wykopapi.dto.Entry;

import java.lang.reflect.Type;
import java.util.List;

public final class StreamIndexRequest extends AbstractRequest<List<Entry>> {
    private final int page;

    private StreamIndexRequest(int page) {
        this.page = page;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("stream").addPathSegment("index")
                .addPathSegment("page").addEncodedPathSegment(String.valueOf(page))
                .build();

        return new Request.Builder()
                .url(url).get()
                .build();
    }

    @Override
    public Type getReturnType() {
        return new TypeToken<List<Entry>>(){}.getType();
    }

    public static class Builder implements ApiRequestBuilder<StreamIndexRequest> {
        private int page;

        public Builder() {
            this.page = 1;
        }

        public Builder setPage(int page) {
            this.page = page > 0 ? page : 1;
            return this;
        }

        @Override
        public StreamIndexRequest build() {
            return new StreamIndexRequest(page);
        }
    }
}
