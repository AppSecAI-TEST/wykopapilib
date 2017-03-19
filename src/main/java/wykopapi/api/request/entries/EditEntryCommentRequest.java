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
public final class EditEntryCommentRequest implements ApiRequest<IdResult> {
    private final String userKey;
    private final int entryId;
    private final int commentId;
    private final String body;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("entries", "editcomment")
                .addMethodParam(String.valueOf(entryId))
                .addMethodParam(String.valueOf(commentId))
                .addApiParam("userkey", userKey)
                .addPostParam("body", body)
                .build();
    }

    @Override
    public Type getReturnType() {
        return IdResult.class;
    }

    public static EditEntryCommentRequestBuilder builder(@NotNull String userKey, int entryId, int commentId, @NotNull String body) {
        return new EditEntryCommentRequestBuilder(userKey, entryId, commentId, body);
    }

    public static class EditEntryCommentRequestBuilder {
        private String userKey;
        private int entryId;
        private int commentId;
        private String body;

        private EditEntryCommentRequestBuilder(@NotNull String userKey, int entryId, int commentId, @NotNull String body) {
            if (Strings.isNullOrEmpty(userKey) || Strings.isNullOrEmpty(body)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            this.userKey = userKey;
            this.entryId = entryId;
            this.commentId = commentId;
            this.body = body;
        }

        public EditEntryCommentRequest build() {
            return new EditEntryCommentRequest(userKey, entryId, commentId, body);
        }
    }
}
