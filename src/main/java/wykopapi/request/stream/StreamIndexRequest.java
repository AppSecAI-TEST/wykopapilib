package wykopapi.request.stream;

import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.request.AbstractRequest;
import wykopapi.request.ApiRequestBuilder;
import wykopapi.dto.Entry;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public final class StreamIndexRequest extends AbstractRequest<List<Entry>> {
    private final int page;

    private StreamIndexRequest(int page) {
        this.page = page;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = addAppKeyAndBuild(urlBuilder()
                .addPathSegment("stream").addPathSegment("index")
                .addPathSegment("page").addEncodedPathSegment(Integer.toString(page)));
        return signRequestAndBuild(new Request.Builder().url(url).get(), url, Collections.emptyMap());
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
