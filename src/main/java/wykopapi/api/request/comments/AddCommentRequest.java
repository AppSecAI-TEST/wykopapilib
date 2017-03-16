package wykopapi.api.request.comments;

import com.google.common.base.Strings;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import wykopapi.api.dto.IdResult;
import wykopapi.api.request.AbstractRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class AddCommentRequest extends AbstractRequest<IdResult> {
    private final String userKey;
    private final int linkId;
    private final int commentId;
    private final String body;
    private final String embedUrl;
    private final File embedFile;

    private AddCommentRequest(String userKey, int linkId, int commentId, String body, String embedUrl, File embedFile) {
        this.userKey = userKey;
        this.linkId = linkId;
        this.commentId = commentId;
        this.body = body;
        this.embedUrl = embedUrl;
        this.embedFile = embedFile;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("comments").addPathSegment("add")
                .addEncodedPathSegment(String.valueOf(linkId)).addEncodedPathSegment(String.valueOf(commentId)) // TODO what if comment id is not set
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
        return IdResult.class;
    }

    public static class Builder implements ApiRequestBuilder<AddCommentRequest> {
        private String userKey;
        private int linkId;
        private int commentId;
        private String body;
        private String embedUrl;
        private File embedFile;

        public Builder(String userKey, int linkId) {
            this.userKey = userKey;
            this.linkId = linkId;
        }

        public Builder setCommentId(int commentId) {
            this.commentId = commentId;
            return this;
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
        public AddCommentRequest build() {
            return new AddCommentRequest(userKey, linkId, commentId, body, embedUrl, embedFile);
        }
    }
}
