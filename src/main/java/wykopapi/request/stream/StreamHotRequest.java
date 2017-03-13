package wykopapi.request.stream;

import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.dto.Entry;
import wykopapi.request.AbstractRequest;
import wykopapi.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.List;

public final class StreamHotRequest extends AbstractRequest<List<Entry>> {
    private final int page;
    private final int period;
    private final boolean clearOutput;

    private StreamHotRequest(int page, int period, boolean clearOutput) {
        this.page = page;
        this.period = period;
        this.clearOutput = clearOutput;
    }

    @Override
    public Request getRequest() {
        HttpUrl.Builder urlBuilder = newUrlBuilder()
                .addPathSegment("stream").addPathSegment("hot")
                .addPathSegment("page").addEncodedPathSegment(String.valueOf(page))
                .addPathSegment("period").addEncodedPathSegment(String.valueOf(period));
        if (clearOutput) urlBuilder.addPathSegment("output").addPathSegment("clear");

        return new Request.Builder()
                .url(urlBuilder.build()).get()
                .build();
    }

    @Override
    public Type getReturnType() {
        return new TypeToken<List<Entry>>(){}.getType();
    }

    public static class Builder implements ApiRequestBuilder<StreamHotRequest> {
        private int page;
        private int period;
        private boolean clearOutput;

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

        public Builder setClearOutput(boolean clearOutput) {
            this.clearOutput = clearOutput;
            return this;
        }

        @Override
        public StreamHotRequest build() {
            return new StreamHotRequest(page, period, clearOutput);
        }
    }
}
