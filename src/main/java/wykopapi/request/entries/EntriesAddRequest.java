package wykopapi.request.entries;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.request.AbstractRequest;
import wykopapi.request.ApiRequestBuilder;
import wykopapi.dto.AddEntry;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class EntriesAddRequest extends AbstractRequest<AddEntry> {
    private final String body;
    private final String userKey;
    private final String embedUrl;
    private final File embedFile;

    private EntriesAddRequest(String body, String userKey, String embedUrl, File embedFile) {
        this.body = body;
        this.userKey = userKey;
        this.embedUrl = embedUrl;
        this.embedFile = embedFile;
    }

    @Override
    public Request getRequest() {
        HttpUrl.Builder urlBuilder = urlBuilder().addPathSegment("entries")
                .addPathSegment("add")
                .addPathSegment("userkey").addEncodedPathSegment(userKey);
        HttpUrl url = addAppKeyAndBuild(urlBuilder);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("body", body);
        if (embedUrl != null) parameters.put("embed", embedUrl);

        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        parameters.forEach(formBodyBuilder::addEncoded);

        Request.Builder requestBuilder = new Request.Builder()
                .url(url).post(formBodyBuilder.build());
        return signRequestAndBuild(requestBuilder, url, parameters);
    }

    @Override
    public Type getReturnType() {
        return AddEntry.class;
    }

    public static class Builder implements ApiRequestBuilder<EntriesAddRequest> {
        private String body;
        private String userKey;

        private String embedUrl;
        private File embedFile;

        public Builder(String body, String userKey) {
            this.body = body;
            this.userKey = userKey;
        }

        public Builder setEmbedUrl(String embedUrl) {
            this.embedUrl = embedUrl;
            return this;
        }

        public Builder setEmbedFile(File embedFile) {
            this.embedFile = embedFile;
            return this;
        }

        public EntriesAddRequest build() {
            return new EntriesAddRequest(body, userKey, embedUrl, embedFile);
        }
    }
}
