package wykopapi.api.request.pm;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.io.File;
import java.lang.reflect.Type;

// TODO check return type, you never know what wykop api returns
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class SendMessageRequest implements ApiRequest<Boolean> {
    private final String userKey;
    private final String userName;
    private final String body;
    private final String embedUrl;
    private final File embedFile;

    @Override
    public Request getRequest() {
        ApiRequestBuilder requestBuilder = new ApiRequestBuilder("pm", "sendmessage")
                .addMethodParam(userName)
                .addApiParam("userkey", userKey);
        if (!Strings.isNullOrEmpty(body)) requestBuilder.addPostParam("body", body);
        if (!Strings.isNullOrEmpty(embedUrl)) requestBuilder.addPostParam("embed", embedUrl);
        if (embedFile != null) requestBuilder.setEmbedFile(embedFile);

        return requestBuilder.build();
    }

    @Override
    public Type getReturnType() {
        return Boolean.class;
    }

    public static SendMessageRequestBuilder builder(@NotNull String userKey, @NotNull String userName) {
        return new SendMessageRequestBuilder(userKey, userName);
    }

    public static class SendMessageRequestBuilder {
        private String userKey;
        private String userName;
        private String body;
        private String embedUrl;
        private File embedFile;

        private SendMessageRequestBuilder(@NotNull String userKey, @NotNull String userName) {
            if (Strings.isNullOrEmpty(userKey) || Strings.isNullOrEmpty(userName)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            this.userKey = userKey;
            this.userName = userName;
        }

        public SendMessageRequestBuilder body(String body) {
            this.body = body;
            return this;
        }

        public SendMessageRequestBuilder embedUrl(String embedUrl) {
            this.embedUrl = embedUrl;
            this.embedFile = null;
            return this;
        }

        public SendMessageRequestBuilder embedFile(File embedFile) {
            this.embedFile = embedFile;
            this.embedUrl = null;
            return this;
        }

        public SendMessageRequest build() {
            return new SendMessageRequest(userKey, userName, body, embedUrl, embedFile);
        }
    }
}
