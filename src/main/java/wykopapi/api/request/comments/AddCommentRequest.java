package wykopapi.api.request.comments;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import wykopapi.api.dto.IdResult;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class AddCommentRequest implements ApiRequest<IdResult> {
    private final String userKey;
    private final int linkId;
    private final int commentId;
    private final String body;
    private final String embedUrl;
    private final File embedFile;

    @Override
    public Request getRequest() {
        ApiRequestBuilder requestBuilder = new ApiRequestBuilder("comments", "add")
                .addMethodParam(String.valueOf(linkId))
                .addMethodParam(String.valueOf(commentId)) // TODO what if comment id is not set
                .addApiParam("userkey", userKey);

        if (!Strings.isNullOrEmpty(body)) requestBuilder.addPostParam("body", body);
        if (!Strings.isNullOrEmpty(embedUrl)) requestBuilder.addPostParam("embed", embedUrl);
        if (embedFile != null) requestBuilder.setEmbedFile(embedFile);

        return requestBuilder.build();
    }

    @Override
    public Type getReturnType() {
        return IdResult.class;
    }

    public static AddCommentRequestBuilder builder(@NotNull String userKey, int linkId) {
        return new AddCommentRequestBuilder(userKey, linkId);
    }

    public static class AddCommentRequestBuilder {
        private String userKey;
        private int linkId;
        private int commentId;
        private String body;
        private String embedUrl;
        private File embedFile;

        private AddCommentRequestBuilder(@NotNull String userKey, int linkId) {
            if (Strings.isNullOrEmpty(userKey)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            if (linkId < 0) {
                throw new IllegalArgumentException("Parameter cannot be negative");
            }
            this.userKey = userKey;
            this.linkId = linkId;
        }

        public AddCommentRequestBuilder commentId(int commentId) {
            if (commentId < 0) {
                throw new IllegalArgumentException("Parameter cannot be negative");
            }
            this.commentId = commentId;
            return this;
        }

        public AddCommentRequestBuilder body(String body) {
            this.body = body;
            return this;
        }

        public AddCommentRequestBuilder embedUrl(String embedUrl) {
            this.embedUrl = embedUrl;
            this.embedFile = null;
            return this;
        }

        public AddCommentRequestBuilder embedFile(File embedFile) {
            this.embedFile = embedFile;
            this.embedUrl = null;
            return this;
        }

        public AddCommentRequest build() {
            return new AddCommentRequest(userKey, linkId, commentId, body, embedUrl, embedFile);
        }
    }
}
