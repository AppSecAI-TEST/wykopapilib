package wykopapi.api;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.dto.Profile;

public final class UserApi {
    private final ApiHelper helper;

    UserApi(ApiHelper helper) {
        this.helper = helper;
    }

    public Result<Profile> login(String username, String password) {
        return login(Authentication.of(username, password));
    }

    public Result<Profile> login(String accountKey) {
        return login(Authentication.of(accountKey));
    }

    public Result<Profile> login(Authentication authentication) {
        HttpUrl url = helper.getUrlBuilder().addPathSegment("user")
                .addPathSegment("login")
                .addPathSegment("appkey").addEncodedPathSegment(helper.getAppKey())
                .build();

        FormBody.Builder formBody = new FormBody.Builder();
        authentication.getAuthParams().forEach(formBody::addEncoded);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("apisign", helper.getApiSign(url, authentication.getAuthParams()))
                .post(formBody.build())
                .build();

        return helper.execute(request, Profile.class);
    }
}
