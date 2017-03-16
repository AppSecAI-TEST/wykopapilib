package wykopapi.api.request.pm;

import com.google.common.base.Strings;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import wykopapi.api.request.AbstractRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

// TODO check return type, you never know what wykop api returns
public final class SendMessageRequest extends AbstractRequest<Boolean> {
    private final String userKey;
    private final String userName;
    private final String body;
    private final String embedUrl;
    private final File embedFile;

    private SendMessageRequest(String userKey, String userName, String body, String embedUrl, File embedFile) {
        this.userKey = userKey;
        this.userName = userName;
        this.body = body;
        this.embedUrl = embedUrl;
        this.embedFile = embedFile;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("pm").addPathSegment("sendmessage")
                .addEncodedPathSegment(userName)
                .addPathSegment("userkey").addEncodedPathSegment(userKey)
                .build();

        Map<String, String> parameters = new HashMap<>();
        if (!Strings.isNullOrEmpty(body)) parameters.put("body", body);
        if (!Strings.isNullOrEmpty(embedUrl)) parameters.put("embed", embedUrl);

        RequestBody requestBody = embedFile == null
                ? createBodyFromParams(parameters)
                : createMultipartBody(parameters, embedFile);

        return new Request.Builder()
                .url(url).post(requestBody)
                .build();
    }

    @Override
    public Type getReturnType() {
        return Boolean.class;
    }

    public static class Builder implements ApiRequestBuilder<SendMessageRequest> {
        private String userKey;
        private String userName;
        private String body;
        private String embedUrl;
        private File embedFile;

        public Builder(String userKey, String userName) {
            this.userKey = userKey;
            this.userName = userName;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Builder setEmbedUrl(String embedUrl) {
            this.embedUrl = embedUrl;
            this.embedFile = null;
            return this;
        }

        public Builder setEmbedFile(File embedFile) {
            this.embedFile = embedFile;
            this.embedUrl = null;
            return this;
        }

        @Override
        public SendMessageRequest build() {
            return new SendMessageRequest(userKey, userName, body, embedUrl, embedFile);
        }
    }
}
