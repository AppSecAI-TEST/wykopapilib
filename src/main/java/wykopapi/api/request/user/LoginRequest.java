package wykopapi.api.request.user;

import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.api.request.AbstractRequest;
import wykopapi.api.dto.Profile;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class LoginRequest extends AbstractRequest<Profile> {
    private final Map<String, String> authParams;

    private LoginRequest(Map<String, String> authParams) {
        this.authParams = new HashMap<>(authParams);
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("user").addPathSegment("login")
                .build();

        return new Request.Builder()
                .url(url).post(createBodyFromParams(authParams))
                .build();
    }

    @Override
    public Type getReturnType() {
        return Profile.class;
    }

    public static class Builder implements ApiRequestBuilder<LoginRequest> {
        private final Map<String, String> params;

        public Builder(String accountKey) {
            this.params = Collections.singletonMap("accountkey", accountKey);
        }

        public Builder(String username, String password) {
            this.params = new HashMap<>();
            params.put("username", username);
            params.put("password", password);
        }

        @Override
        public LoginRequest build() {
            return new LoginRequest(Collections.unmodifiableMap(params));
        }
    }
}