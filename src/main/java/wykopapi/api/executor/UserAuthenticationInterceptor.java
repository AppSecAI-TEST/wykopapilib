package wykopapi.api.executor;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class UserAuthenticationInterceptor implements Interceptor {
    private String userKey;

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (userKey == null) return chain.proceed(chain.request());
        HttpUrl url = chain.request().url().newBuilder()
                .addPathSegment("userkey").addEncodedPathSegment(userKey)
                .build();
        Request request = chain.request().newBuilder()
                .url(url)
                .build();
        return chain.proceed(request);
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
}
