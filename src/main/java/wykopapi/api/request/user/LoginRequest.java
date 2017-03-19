package wykopapi.api.request.user;

import com.google.common.base.Strings;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import wykopapi.api.dto.Profile;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class LoginRequest implements ApiRequest<Profile> {
    private final Map<String, String> authParams;

    private LoginRequest(Map<String, String> authParams) {
        this.authParams = new HashMap<>(authParams);
    }

    @Override
    public Request getRequest() {
        ApiRequestBuilder requestBuilder = new ApiRequestBuilder("user", "login");
        authParams.forEach(requestBuilder::addPostParam);
        return requestBuilder.build();
    }

    @Override
    public Type getReturnType() {
        return Profile.class;
    }

    public static LoginRequestBuilder builder(@NotNull String accountKey) {
        return new LoginRequestBuilder()
                .addParameter("accountkey", accountKey);
    }

    public static LoginRequestBuilder builder(@NotNull String username, @NotNull String password) {
        return new LoginRequestBuilder()
                .addParameter("username", username)
                .addParameter("password", password);
    }

    public static class LoginRequestBuilder {
        private final Map<String, String> params;

        private LoginRequestBuilder() {
            this.params = new HashMap<>();
        }

        private LoginRequestBuilder addParameter(@NotNull String key, @NotNull String value) {
            if (Strings.isNullOrEmpty(key) || Strings.isNullOrEmpty(value)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            params.put(key, value);
            return this;
        }

        public LoginRequest build() {
            return new LoginRequest(Collections.unmodifiableMap(params));
        }
    }
}