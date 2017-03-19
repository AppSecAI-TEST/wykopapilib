package wykopapi.api.request;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

public final class ApiRequestBuilder {
    private final String resource;
    private final String method;

    private final List<String> methodParams;
    private final Map<String, String> apiParams;
    private final Map<String, String> postParams;
    private File embedFile;

    public ApiRequestBuilder(@NotNull String resource, @NotNull String method) {
        if (Strings.isNullOrEmpty(resource) || Strings.isNullOrEmpty(method)) {
            throw new IllegalArgumentException("Resource and method names cannot be null or empty");
        }

        this.methodParams = Lists.newArrayList();
        this.apiParams = Maps.newHashMap();
        this.postParams = Maps.newHashMap();

        this.resource = resource;
        this.method = method;
    }

    public ApiRequestBuilder addMethodParam(@NotNull String value) {
        if (Strings.isNullOrEmpty(value)) {
            throw new IllegalArgumentException("Parameter value cannot be null or empty");
        }
        methodParams.add(value);
        return this;
    }

    public ApiRequestBuilder addMethodParam(@NotNull String value, boolean condition) {
        if (condition) addMethodParam(value);
        return this;
    }

    public ApiRequestBuilder addApiParam(@NotNull String key, @NotNull String value) {
        if (Strings.isNullOrEmpty(key) || Strings.isNullOrEmpty(value)) {
            throw new IllegalArgumentException("Parameter key or value cannot be null or empty");
        }
        apiParams.put(key, value);
        return this;
    }

    public ApiRequestBuilder addApiParam(@NotNull String key, @NotNull String value, boolean condition) {
        if (condition) addApiParam(key, value);
        return this;
    }

    public ApiRequestBuilder addPostParam(@NotNull String key, @NotNull String value) {
        if (Strings.isNullOrEmpty(key) || Strings.isNullOrEmpty(value)) {
            throw new IllegalArgumentException("Parameter key or value cannot be null or empty");
        }
        postParams.put(key, value);
        return this;
    }

    public ApiRequestBuilder addPostParam(@NotNull String key, @NotNull String value, boolean condition) {
        if (condition) addPostParam(key, value);
        return this;
    }

    public ApiRequestBuilder setEmbedFile(File file) {
        embedFile = file;
        return this;
    }

    public Request build() {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme("https").host("a.wykop.pl")
                .addEncodedPathSegment(resource)
                .addEncodedPathSegment(method);
        methodParams.forEach(urlBuilder::addEncodedPathSegment);
        apiParams.forEach((k, v) -> urlBuilder.addEncodedPathSegment(k).addEncodedPathSegment(v));

        Request.Builder requestBuilder = new Request.Builder()
                .url(urlBuilder.build());
        if (postParams == null || postParams.isEmpty()) {
            requestBuilder.get();
        }
        else {
            RequestBody requestBody = embedFile == null
                    ? createBodyFromParams(postParams)
                    : createMultipartBody(postParams, embedFile);
            requestBuilder.post(requestBody);
        }
        return requestBuilder.build();
    }


    private FormBody createBodyFromParams(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        params.forEach(builder::addEncoded);
        return builder.build();
    }

    private MultipartBody createMultipartBody(Map<String, String> params, File file) {
        String extension = file.getName().substring(file.getName().lastIndexOf('.') + 1);
        MediaType mediaType;
        switch (extension) {
            case "jpg":
            case "jpeg":
                mediaType = MediaType.parse("image/jpeg");
                break;
            case "png":
                mediaType = MediaType.parse("image/png");
                break;
            case "gif":
                mediaType = MediaType.parse("image/gif");
                break;
            default:
                mediaType = null;
                break;
        }
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        params.forEach(bodyBuilder::addFormDataPart);
        bodyBuilder.addFormDataPart("embed", file.getName(),
                RequestBody.create(mediaType, file));

        return bodyBuilder.build();
    }
}
