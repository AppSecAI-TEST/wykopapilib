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

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DeleteCommentRequest implements ApiRequest<IdResult> {
    private final String userKey;
    private final int commentId;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("comments", "delete")
                .addMethodParam(String.valueOf(commentId))
                .addApiParam("userkey", userKey)
                .build();
    }

    @Override
    public Type getReturnType() {
        return IdResult.class;
    }

    public static DeleteCommentRequestBuilder builder(@NotNull String userKey, int commentId) {
        return new DeleteCommentRequestBuilder(userKey, commentId);
    }

    public static class DeleteCommentRequestBuilder {
        private String userKey;
        private int commentId;

        private DeleteCommentRequestBuilder(@NotNull String userKey, int commentId) {
            if (Strings.isNullOrEmpty(userKey)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            if (commentId < 0) {
                throw new IllegalArgumentException("Parameter cannot be negative");
            }
            this.userKey = userKey;
            this.commentId = commentId;
        }

        public DeleteCommentRequest build() {
            return new DeleteCommentRequest(userKey, commentId);
        }
    }
}
