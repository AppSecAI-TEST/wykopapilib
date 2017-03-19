package wykopapi.api.request.entries;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class FavoriteEntryRequest implements ApiRequest<Boolean> {
    private final String userKey;
    private final int entryId;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("entries", "favorite")
                .addMethodParam(String.valueOf(entryId))
                .addApiParam("userkey", userKey)
                .build();
    }

    @Override
    public Type getReturnType() {
        return Boolean.class;
    }

    public static FavoriteEntryRequestBuilder builder(@NotNull String userKey, int entryId) {
        return new FavoriteEntryRequestBuilder(userKey, entryId);
    }

    public static class FavoriteEntryRequestBuilder {
        private String userKey;
        private int entryId;

        private FavoriteEntryRequestBuilder(@NotNull String userKey, int entryId) {
            if (Strings.isNullOrEmpty(userKey)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            if (entryId < 0) {
                throw new IllegalArgumentException("Parameter cannot be negative");
            }
            this.userKey = userKey;
            this.entryId = entryId;
        }

        public FavoriteEntryRequest build() {
            return new FavoriteEntryRequest(userKey, entryId);
        }
    }
}
