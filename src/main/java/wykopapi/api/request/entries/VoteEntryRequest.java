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
public final class VoteEntryRequest implements ApiRequest<VoteEntry> {
    private final String userKey;
    private final int entryId;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("entries", "vote")
                .addMethodParam("entry")
                .addMethodParam(String.valueOf(entryId))
                .addApiParam("userkey", userKey)
                .build();
    }

    @Override
    public Type getReturnType() {
        return VoteEntry.class;
    }

    public static VoteEntryRequestBuilder builder(@NotNull String userKey, int entryId) {
        return new VoteEntryRequestBuilder(userKey, entryId);
    }

    public static class VoteEntryRequestBuilder {
        private String userKey;
        private int entryId;

        private VoteEntryRequestBuilder(@NotNull String userKey, int entryId) {
            if (Strings.isNullOrEmpty(userKey)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            if (entryId < 0) {
                throw new IllegalArgumentException("Parameter cannot be negative");
            }
            this.userKey = userKey;
            this.entryId = entryId;
        }

        public VoteEntryRequest build() {
            return new VoteEntryRequest(userKey, entryId);
        }
    }
}
