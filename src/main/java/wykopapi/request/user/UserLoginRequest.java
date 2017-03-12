package wykopapi.request.user;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.Authentication;
import wykopapi.request.AbstractRequest;
import wykopapi.request.ApiRequestBuilder;
import wykopapi.dto.Profile;

import java.lang.reflect.Type;

public final class UserLoginRequest extends AbstractRequest<Profile> {
    private final Authentication authentication;

    private UserLoginRequest(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = addAppKeyAndBuild(urlBuilder()
                .addPathSegment("user").addPathSegment("login"));
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        authentication.getAuthParams().forEach(formBodyBuilder::addEncoded);

        return signRequestAndBuild(new Request.Builder()
                .url(url)
                .post(formBodyBuilder.build()),
                url, authentication.getAuthParams());
    }

    @Override
    public Type getReturnType() {
        return Profile.class;
    }

    public static class Builder implements ApiRequestBuilder<UserLoginRequest> {
        private String username;
        private String password;
        private String accountKey;

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setAccountKey(String accountKey) {
            this.accountKey = accountKey;
            return this;
        }

        @Override
        public UserLoginRequest build() {
            if (accountKey != null) return new UserLoginRequest(Authentication.of(accountKey));
            else if (username != null && password != null) return new UserLoginRequest(Authentication.of(username, password));
            else throw new IllegalStateException("Not enough data");
        }
    }
}
