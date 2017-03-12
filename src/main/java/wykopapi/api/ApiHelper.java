package wykopapi.api;


import com.google.common.base.Joiner;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wykopapi.config.LocalDateTimeGsonAdapter;
import wykopapi.dto.Error;
import wykopapi.dto.ErrorInfo;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

final class ApiHelper {
    private static final String SCHEME = "https";
    private static final String HOST = "a.wykop.pl";

    private final Logger logger = LoggerFactory.getLogger(ApiHelper.class);
    private final MessageDigest messageDigest;

    private final String appKey;
    private final String secret;

    private final OkHttpClient httpClient;
    private final Gson gson;

    ApiHelper(String appKey, String secret) {
        this.appKey = appKey;
        this.secret = secret;
        this.httpClient = new OkHttpClient.Builder().build();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeGsonAdapter())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        try {
            this.messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.error("Could not get MD5 instance");
            throw new RuntimeException(e);
        }
    }

    HttpUrl.Builder getUrlBuilder() {
        return new HttpUrl.Builder()
                .scheme(SCHEME)
                .host(HOST);
    }

    String getAppKey() {
        return appKey;
    }

    String getApiSign(HttpUrl url, Map<String, String> parameters) {
        List<String> sortedParams = parameters.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(Map.Entry::getValue).collect(Collectors.toList());

        StringBuilder builder = new StringBuilder();
        builder.append(secret);
        builder.append(url);
        Joiner.on(',').appendTo(builder, sortedParams);

        byte[] bytes = builder.toString().getBytes(Charset.forName("UTF-8"));
        messageDigest.reset();
        byte[] md5 = messageDigest.digest(bytes);

        return DatatypeConverter.printHexBinary(md5);
    }

    <T> Result<T> execute(Request request, TypeToken<T> typeToken) {
        try {
            Response response = httpClient.newCall(request).execute();
            String json = response.body().string();
            System.out.println(json);
            return json.matches("^\\{\"error\":\\{\"code\":\\d+,\"message\":\".*\"}}$")
                    ? Result.error(gson .fromJson(json, Error.class).getError())
                    : Result.success(gson.fromJson(json, typeToken.getType()));
        } catch (IOException e) {
            logger.error("Request failed: " + request.url() + ", ex: " + e.getMessage());
            return Result.error(new ErrorInfo(-1, "Request failed"));
        } catch (JsonIOException | JsonSyntaxException e) {
            logger.error("Gson failed for request: " + request.url() + ", ex: " + e.getMessage());
            return Result.error(new ErrorInfo(-2, "Json parsing failed"));
        }
    }

    <T> Result<T> execute(Request request, Class<T> tClass) {
        return execute(request, TypeToken.get(tClass));
    }
}