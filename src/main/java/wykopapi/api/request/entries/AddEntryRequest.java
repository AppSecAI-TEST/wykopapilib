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
public final class AddEntryRequest implements ApiRequest<IdResult> {
    private final String userKey;
    private final String body;
    private final String embedUrl;
    private final File embedFile;

    @Override
    public Request getRequest() {
        ApiRequestBuilder requestBuilder = new ApiRequestBuilder("entries", "add")
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

    public static AddEntryRequestBuilder builder(@NotNull String userKey) {
        return new AddEntryRequestBuilder(userKey);
    }

    public static class AddEntryRequestBuilder {
        private String userKey;
        private String body;
        private String embedUrl;
        private File embedFile;

        private AddEntryRequestBuilder(@NotNull String userKey) {
            if (Strings.isNullOrEmpty(userKey)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            this.userKey = userKey;
        }

        public AddEntryRequestBuilder body(String body) {
            this.body = body;
            return this;
        }

        public AddEntryRequestBuilder embedUrl(String embedUrl) {
            this.embedUrl = embedUrl;
            this.embedFile = null;
            return this;
        }

        public AddEntryRequestBuilder embedFile(File embedFile) {
            this.embedFile = embedFile;
            this.embedUrl = null;
            return this;
        }

        public AddEntryRequest build() {
            return new AddEntryRequest(userKey, body, embedUrl, embedFile);
        }
    }
}
