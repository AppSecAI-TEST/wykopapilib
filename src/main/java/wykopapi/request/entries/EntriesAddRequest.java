package wykopapi.request.entries;

import com.google.common.base.Strings;
import okhttp3.*;
import wykopapi.request.AbstractRequest;
import wykopapi.request.ApiRequestBuilder;
import wykopapi.dto.AddEntry;

import java.io.File;
import java.lang.reflect.Type;

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
        HttpUrl url = newUrlBuilder()
                .addPathSegment("entries").addPathSegment("add")
                .addPathSegment("userkey").addEncodedPathSegment(userKey)
                .build();

        RequestBody requestBody = null;
        if (embedFile != null) {
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("body", body)
                    .addFormDataPart("embed", "embed.jpg",
                            RequestBody.create(MediaType.parse("image/jpeg"), embedFile))
                    .build();
        }
        else {
            FormBody.Builder builder = new FormBody.Builder()
                    .addEncoded("body", body);
            if (!Strings.isNullOrEmpty(embedUrl)) builder.addEncoded("embed", embedUrl);
            requestBody = builder.build();
        }

        return new Request.Builder()
                .url(url).post(requestBody)
                .build();
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
