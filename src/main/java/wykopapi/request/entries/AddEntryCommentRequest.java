package wykopapi.request.entries;

import com.google.common.base.Strings;
import okhttp3.*;
import wykopapi.dto.EntryOperation;
import wykopapi.request.AbstractRequest;

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
}
