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

// TODO check if its possible to change embed url/file
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class EditEntryRequest implements ApiRequest<IdResult> {
    private final String userKey;
    private final int entryId;
    private final String body;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("entries", "edit")
                .addMethodParam(String.valueOf(entryId))
                .addApiParam("userkey", userKey)
                .addPostParam("body", body)
                .build();
    }

    @Override
    public Type getReturnType() {
        return IdResult.class;
    }

    public static EditEntryRequestBuilder builder(@NotNull String userKey, int entryId, @NotNull String body) {
        return new EditEntryRequestBuilder(userKey, entryId, body);
    }

    public static class EditEntryRequestBuilder {
        private String userKey;
        private int entryId;
        private String body;

        private EditEntryRequestBuilder(@NotNull String userKey, int entryId, @NotNull String body) {
            if (Strings.isNullOrEmpty(userKey) || Strings.isNullOrEmpty(body)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            this.userKey = userKey;
            this.entryId = entryId;
            this.body = body;
        }

        public EditEntryRequest build() {
            return new EditEntryRequest(userKey, entryId, body);
        }
    }
}
