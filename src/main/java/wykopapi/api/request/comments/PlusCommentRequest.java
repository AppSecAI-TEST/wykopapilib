package wykopapi.api.request.comments;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;

// TODO proper return type
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlusCommentRequest implements ApiRequest<String> {
    private final String userKey;
    private final int linkId;
    private final int commentId;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("comments", "plus")
                .addMethodParam(String.valueOf(linkId))
                .addMethodParam(String.valueOf(commentId))
                .addApiParam("userkey", userKey)
                .build();
    }

    @Override
    public Type getReturnType() {
        return String.class;
    }

    public static PlusCommentRequestBuilder builder(@NotNull String userKey, int linkId, int commentId) {
        return new PlusCommentRequestBuilder(userKey, linkId, commentId);
    }

    public static class PlusCommentRequestBuilder {
        private String userKey;
        private int linkId;
        private int commentId;

        private PlusCommentRequestBuilder(@NotNull String userKey, int linkId, int commentId) {
            if (Strings.isNullOrEmpty(userKey)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            if (linkId < 0 || commentId < 0) {
                throw new IllegalArgumentException("Parameter cannot be negative");
            }
            this.userKey = userKey;
            this.linkId = linkId;
            this.commentId = commentId;
        }

        public PlusCommentRequest build() {
            return new PlusCommentRequest(userKey, linkId, commentId);
        }
    }
}
