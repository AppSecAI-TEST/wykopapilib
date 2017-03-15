package wykopapi.api.executor;

import com.google.gson.*;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wykopapi.api.dto.Error;
import wykopapi.api.dto.ErrorInfo;
import wykopapi.api.dto.Profile;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.AuthApiRequest;
import wykopapi.api.request.Result;
import wykopapi.api.request.user.LoginRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class UserAuthRequestExecutor {
    private static final int SESSION_ERROR_CODE = 12;
    private final Logger logger = LoggerFactory.getLogger(UserAuthRequestExecutor.class);

    private final LoginRequest loginRequest;
    private final UserAuthenticationInterceptor userAuthenticationInterceptor;
    private final OkHttpClient httpClient;
    private final Gson gson;

    public UserAuthRequestExecutor(String appKey, String secret, String accountKey) {
        this.loginRequest = new LoginRequest.Builder(accountKey).build();
        this.userAuthenticationInterceptor = new UserAuthenticationInterceptor();
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        List<Interceptor> interceptors = httpClientBuilder.interceptors();
        interceptors.add(this.userAuthenticationInterceptor);
        interceptors.add(new AuthInterceptor(appKey, secret));

        this.httpClient = httpClientBuilder.build();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonAdapter())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

//    public <T, R extends AuthApiRequest<T>> Result<T> execute(R request) {
//        Result<T> result = doRequest(request);
//        if (result.isError() && result.getErrorCode() == SESSION_ERROR_CODE) {
//            Result<Profile> profile = doRequest(loginRequest);
//            if (profile.isError() || profile.get().getUserkey() == null) return result;
//            userAuthenticationInterceptor.setUserKey(profile.get().getUserkey());
//            return doRequest(request);
//        }
//        return result;
//    }
//
//    public <T, R extends ApiRequest<T>> Result<T> doRequest(R request) {
//        String json = null;
//        try {
//            Response response = httpClient.newCall(request.getRequest()).execute();
//            json = response.body().string();
//            return json.matches("^\\{\"error\":\\{\"code\":\\d+,\"message\":\".*\"}}$")
//                    ? Result.error(gson .fromJson(json, Error.class).getError())
//                    : Result.success(gson.fromJson(json, request.getReturnType()));
//        } catch (IOException e) {
//            logger.error("Request failed: " + e.getMessage());
//            return Result.error(new ErrorInfo(-1, "Request failed"));
//        } catch (JsonIOException | JsonSyntaxException e) {
//            logger.error("Json parsing failed for json: " + json);
//            return Result.error(new ErrorInfo(-2, "Json parsing failed"));
//        }
//    }
}
