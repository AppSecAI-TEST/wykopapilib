package wykopapi.api.request.entries;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import wykopapi.api.dto.IdResult;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.io.File;
import java.lang.reflect.Type;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class AddEntryCommentRequest implements ApiRequest<IdResult> {
    private final String userKey;
    private final int entryId;
    private final String body;
    private final String embedUrl;
    private final File embedFile;

    @Override
    public Request getRequest() {
        ApiRequestBuilder requestBuilder = new ApiRequestBuilder("entries", "addcomment")
                .addMethodParam(String.valueOf(entryId))
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

    public static AddEntryCommentRequestBuilder builder(@NotNull String userKey, int entryId) {
        return new AddEntryCommentRequestBuilder(userKey, entryId);
    }

    public static class AddEntryCommentRequestBuilder {
        private String userKey;
        private int entryId;
        private String body;
        private String embedUrl;
        private File embedFile;

        private AddEntryCommentRequestBuilder(@NotNull String userKey, int entryId) {
            if (Strings.isNullOrEmpty(userKey)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            if (entryId < 0) {
                throw new IllegalArgumentException("Parameter cannot be negative");
            }
            this.entryId = entryId;
            this.userKey = userKey;
        }

        public AddEntryCommentRequestBuilder body(String body) {
            this.body = body;
            return this;
        }

        public AddEntryCommentRequestBuilder embedUrl(String embedUrl) {
            this.embedUrl = embedUrl;
            this.embedFile = null;
            return this;
        }

        public AddEntryCommentRequestBuilder embedFile(File embedFile) {
            this.embedFile = embedFile;
            this.embedUrl = null;
            return this;
        }

        public AddEntryCommentRequest build() {
            return new AddEntryCommentRequest(userKey, entryId, body, embedUrl, embedFile);
        }
    }
}
