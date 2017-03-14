package wykopapi.api.request.rank;

import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.api.dto.Profile;
import wykopapi.api.request.AbstractRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.List;

public final class RankIndexRequest extends AbstractRequest<List<Profile>> {
    private final RankOrder order;
    private final int page;

    private RankIndexRequest(RankOrder order, int page) {
        this.order = order;
        this.page = page;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("rank").addPathSegment("index")
                .addEncodedPathSegment(order.toString())
                .addPathSegment("page").addEncodedPathSegment(String.valueOf(page))
                .build();

        return new Request.Builder()
                .url(url).get()
                .build();
    }

    @Override
    public Type getReturnType() {
        return new TypeToken<List<Profile>>(){}.getType();
    }

    public static class Builder implements ApiRequestBuilder<RankIndexRequest> {
        private RankOrder order;
        private int page;

        public Builder() {
            this.order = RankOrder.RANK;
            this.page = 1;
        }

        public Builder setOrder(RankOrder order) {
            this.order = order;
            return this;
        }

        public Builder setPage(int page) {
            this.page = page > 0 ? page : 1;
            return this;
        }

        @Override
        public RankIndexRequest build() {
            return new RankIndexRequest(order, page);
        }
    }
}
