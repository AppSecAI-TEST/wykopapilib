package wykopapi.request.stream;

import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.dto.Entry;
import wykopapi.request.AbstractRequest;
import wykopapi.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.List;

public class StreamHotRequest extends AbstractRequest<List<Entry>> {
    private final int page;
    private final int period;

    private StreamHotRequest(int page, int period) {
        this.page = page;
        this.period = period;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("stream").addPathSegment("hot")
                .addPathSegment("page").addEncodedPathSegment(String.valueOf(page))
                .addPathSegment("period").addEncodedPathSegment(String.valueOf(period))
                .build();

        return new Request.Builder()
                .url(url).get()
                .build();
    }

    @Override
    public Type getReturnType() {
        return new TypeToken<List<Entry>>(){}.getType();
    }

    public static class Builder implements ApiRequestBuilder<StreamHotRequest> {
        private int page;
        private int period;

        public Builder() {
            this.page = 1;
            this.period = 12;
        }

        public Builder setPage(int page) {
            this.page = page > 0 ? page : 1;
            return this;
        }

        public Builder setPeriod(int period) {
            this.period = period > 0 ? period : 1;
            return this;
        }

        @Override
        public StreamHotRequest build() {
            return new StreamHotRequest(page, period);
        }
    }
}
