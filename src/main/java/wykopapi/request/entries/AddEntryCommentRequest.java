package wykopapi.request.entries;

import com.google.common.base.Strings;
import okhttp3.*;
import wykopapi.dto.EntryOperation;
import wykopapi.request.AbstractRequest;
import wykopapi.request.ApiRequestBuilder;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class AddEntryCommentRequest extends AbstractRequest<EntryOperation> {
    private final String userKey;
    private final int entryId;
    private final String body;
    private final String embedUrl;
    private final File embedFile;

    private AddEntryCommentRequest(String userKey, int entryId, String body, String embedUrl, File embedFile) {
        this.userKey = userKey;
        this.entryId = entryId;
        this.body = body;
        this.embedUrl = embedUrl;
        this.embedFile = embedFile;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("entries").addPathSegment("addcomment")
                .addEncodedPathSegment(String.valueOf(entryId))
                .addPathSegment("userkey").addEncodedPathSegment(userKey)
                .build();

        Map<String, String> parameters = new HashMap<>();
        parameters.put("body", body);
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
        return EntryOperation.class;
    }

    public static class Builder implements ApiRequestBuilder<AddEntryCommentRequest> {
        private String body;
        private String userKey;
        private int entryId;
        private String embedUrl;
        private File embedFile;

        public Builder(String userKey, int entryId, String body) {
            this.body = body;
            this.entryId = entryId;
            this.userKey = userKey;
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

        public AddEntryCommentRequest build() {
            return new AddEntryCommentRequest(userKey, entryId, body, embedUrl, embedFile);
        }
    }
}
