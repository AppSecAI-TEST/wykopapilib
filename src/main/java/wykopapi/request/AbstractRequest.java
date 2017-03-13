package wykopapi.request;

import com.google.common.base.Joiner;
import com.google.gson.reflect.TypeToken;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.properties.PropertiesService;
import wykopapi.properties.PropertiesServiceFactory;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractRequest<T> implements ApiRequest<T> {
    private static final String SCHEME = "http";
    private static final String HOST = "a.wykop.pl";

    protected AbstractRequest() {
    }

    @Override
    public abstract Request getRequest();

    protected HttpUrl.Builder newUrlBuilder() {
        return new HttpUrl.Builder()
                .scheme(SCHEME)
                .host(HOST);
    }

    protected FormBody createBodyFromParams(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        params.forEach(builder::addEncoded);
        return builder.build();
    }
}
