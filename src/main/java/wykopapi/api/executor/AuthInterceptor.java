package wykopapi.api.executor;

import com.google.common.base.Joiner;
import okhttp3.*;
import wykopapi.api.extractor.Extractor;
import wykopapi.api.extractor.ParameterExtractor;
import wykopapi.api.request.RequestUtils;

import java.io.*;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

final class AuthInterceptor implements Interceptor {
    private final String appKey;
    private final String secret;
    private final RequestUtils requestUtils;
    private final ParameterExtractor<RequestBody> parameterExtractor;

    AuthInterceptor(String appKey, String secret) {
        this.appKey = appKey;
        this.secret = secret;
        this.requestUtils = new RequestUtils();
        this.parameterExtractor = new Extractor();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpUrl url = chain.request().url().newBuilder()
                .addPathSegment("appkey").addEncodedPathSegment(appKey)
                .build();

        Map<String, String> parameters = parameterExtractor.extract(chain.request().body());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(secret).append(url);
        Joiner.on(',').appendTo(stringBuilder, parameters
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(Map.Entry::getValue).collect(Collectors.toList()));

        Request request = chain.request().newBuilder()
                .url(url)
                .addHeader("apisign", requestUtils.getMD5(stringBuilder.toString()))
                .build();

        return chain.proceed(request);
    }
}
