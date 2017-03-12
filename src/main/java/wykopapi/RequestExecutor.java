package wykopapi;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wykopapi.dto.Result;
import wykopapi.request.ApiRequest;
import wykopapi.config.LocalDateTimeGsonAdapter;
import wykopapi.dto.Error;
import wykopapi.dto.ErrorInfo;

import java.io.IOException;
import java.time.LocalDateTime;

public final class RequestExecutor {
    private final Logger logger = LoggerFactory.getLogger(RequestExecutor.class);

    private final OkHttpClient httpClient;
    private final Gson gson;

    public RequestExecutor() {
        this.httpClient = new OkHttpClient.Builder()
                .build();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeGsonAdapter())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    public <T, R extends ApiRequest<T>> Result<T> execute(R request) {
        String json = null;
        try {
            Response response = httpClient.newCall(request.getRequest()).execute();
            json = response.body().string();
            return json.matches("^\\{\"error\":\\{\"code\":\\d+,\"message\":\".*\"}}$")
                    ? Result.error(gson .fromJson(json, Error.class).getError())
                    : Result.success(gson.fromJson(json, request.getReturnType()));
        } catch (IOException e) {
            logger.error("Request failed: " + e.getMessage());
            return Result.error(new ErrorInfo(-1, "Request failed"));
        } catch (JsonIOException | JsonSyntaxException e) {
            logger.error("Json parsing failed for json: " + json);
            return Result.error(new ErrorInfo(-2, "Json parsing failed"));
        }
    }
}
