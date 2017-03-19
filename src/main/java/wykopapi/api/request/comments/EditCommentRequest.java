package wykopapi.api.request.comments;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import wykopapi.api.dto.IdResult;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.Collections;

// TODO check if its possible to change embed url/file
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class EditCommentRequest implements ApiRequest<IdResult> {
    private final String userKey;
    private final int commentId;
    private final String body;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("comments", "edit")
                .addMethodParam(String.valueOf(commentId))
                .addApiParam("userkey", userKey)
                .addPostParam("body", body)
                .build();
    }

    @Override
    public Type getReturnType() {
        return IdResult.class;
    }

    public static EditCommentRequestBuilder builder(@NotNull String userKey, int commentId, @NotNull String body) {
        return new EditCommentRequestBuilder(userKey, commentId, body);
    }

    public static class EditCommentRequestBuilder {
        private String userKey;
        private int commentId;
        private String body;

        private EditCommentRequestBuilder(@NotNull String userKey, int commentId, @NotNull String body) {
            if (Strings.isNullOrEmpty(userKey) || Strings.isNullOrEmpty(body)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            if (commentId < 0) {
                throw new IllegalArgumentException("Parameter cannot be negative");
            }
            this.userKey = userKey;
            this.commentId = commentId;
            this.body = body;
        }

        public EditCommentRequest build() {
            return new EditCommentRequest(userKey, commentId, body);
        }
    }
}
