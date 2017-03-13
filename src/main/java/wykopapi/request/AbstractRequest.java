package wykopapi.request;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.util.Map;

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
