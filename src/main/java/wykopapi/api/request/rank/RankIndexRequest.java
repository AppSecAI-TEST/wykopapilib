package wykopapi.api.request.rank;

import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import wykopapi.api.dto.Profile;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class RankIndexRequest implements ApiRequest<List<Profile>> {
    private final RankOrder order;
    private final int page;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("rank", "index")
                .addMethodParam(order.toString())
                .addApiParam("page", String.valueOf(page))
                .build();
    }

    @Override
    public Type getReturnType() {
        return new TypeToken<List<Profile>>(){}.getType();
    }

    public static RankIndexRequestBuilder builder() {
        return new RankIndexRequestBuilder();
    }

    public static class RankIndexRequestBuilder {
        private RankOrder order;
        private int page;

        private RankIndexRequestBuilder() {
            this.order = RankOrder.RANK;
            this.page = 1;
        }

        public RankIndexRequestBuilder order(@NotNull RankOrder order) {
            if (Objects.isNull(order)) throw new NullPointerException("Parameter cannot be null");
            this.order = order;
            return this;
        }

        public RankIndexRequestBuilder page(int page) {
            this.page = page > 0 ? page : 1;
            return this;
        }

        public RankIndexRequest build() {
            return new RankIndexRequest(order, page);
        }
    }
}
