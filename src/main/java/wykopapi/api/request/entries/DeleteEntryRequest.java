package wykopapi.api.request.entries;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import wykopapi.api.dto.IdResult;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DeleteEntryRequest implements ApiRequest<IdResult> {
    private final String userKey;
    private final int entryId;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("entries", "delete")
                .addMethodParam(String.valueOf(entryId))
                .addApiParam("userkey", userKey)
                .build();
    }

    @Override
    public Type getReturnType() {
        return IdResult.class;
    }

    public static DeleteEntryRequestBuilder builder(@NotNull String userKey, int entryId) {
        return new DeleteEntryRequestBuilder(userKey, entryId);
    }

    public static class DeleteEntryRequestBuilder {
        private String userKey;
        private int entryId;

        private DeleteEntryRequestBuilder(@NotNull String userKey, int entryId) {
            if (Strings.isNullOrEmpty(userKey)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            if (entryId < 0) {
                throw new IllegalArgumentException("Parameter cannot be negative");
            }
            this.userKey = userKey;
            this.entryId = entryId;
        }

        public DeleteEntryRequest build() {
            return new DeleteEntryRequest(userKey, entryId);
        }
    }
}
