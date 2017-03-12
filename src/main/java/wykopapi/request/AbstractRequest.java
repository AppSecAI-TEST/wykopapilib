package wykopapi.request;

import com.google.common.base.Joiner;
import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.properties.PropertiesService;
import wykopapi.properties.PropertiesServiceFactory;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractRequest<T> implements ApiRequest<T> {
    private static final String SCHEME = "https";
    private static final String HOST = "a.wykop.pl";

    private static final String APP_KEY_FIELD = "appkey";
    private static final String API_SIGN_FIELD = "apisign";

    private final PropertiesService propertiesService;
    private final RequestUtils requestUtils;

    protected AbstractRequest() {
        propertiesService = PropertiesServiceFactory.getPropertiesService();
        requestUtils = new RequestUtils();
    }

    @Override
    public abstract Request getRequest();

    public TypeToken<T> getTypeToken() {
        return new TypeToken<T>(){};
    }

    protected HttpUrl.Builder urlBuilder() {
        return new HttpUrl.Builder()
                .scheme(SCHEME)
                .host(HOST);
    }

    protected HttpUrl addAppKeyAndBuild(HttpUrl.Builder builder) {
        return builder.addPathSegment(APP_KEY_FIELD).addEncodedPathSegment(propertiesService.getAppKey())
                .build();
    }

    protected Request signRequestAndBuild(Request.Builder builder, HttpUrl url, Map<String, String> parameters) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(propertiesService.getSecret()).append(url);
        Joiner.on(',').appendTo(stringBuilder, parameters
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(Map.Entry::getValue).collect(Collectors.toList()));

        return builder.addHeader(API_SIGN_FIELD, requestUtils.getMD5(stringBuilder.toString())).build();
    }
}
