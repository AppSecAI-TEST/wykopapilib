package wykopapi.api.request.entries;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import wykopapi.api.dto.VoteEntry;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UnvoteEntryRequest implements ApiRequest<VoteEntry> {
    private final String userKey;
    private final int entryId;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("entries", "unvote")
                .addMethodParam("entry")
                .addMethodParam(String.valueOf(entryId))
                .addApiParam("userkey", userKey)
                .build();
    }

    @Override
    public Type getReturnType() {
        return VoteEntry.class;
    }

    public static UnvoteEntryRequestBuilder builder(@NotNull String userKey, int entryId) {
        return new UnvoteEntryRequestBuilder(userKey, entryId);
    }

    public static class UnvoteEntryRequestBuilder {
        private String userKey;
        private int entryId;

        private UnvoteEntryRequestBuilder(@NotNull String userKey, int entryId) {
            if (Strings.isNullOrEmpty(userKey)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            if (entryId < 0) {
                throw new IllegalArgumentException("Parameter cannot be negative");
            }
            this.userKey = userKey;
            this.entryId = entryId;
        }

        public UnvoteEntryRequest build() {
            return new UnvoteEntryRequest(userKey, entryId);
        }
    }
}
