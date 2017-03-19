package wykopapi.api.request.tag;

import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;

@RequiredArgsConstructor
public class TagIndexRequest implements ApiRequest<String> {
    private final String tag;
    private final int page;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("tag", "index")
                .addMethodParam(tag)
                .addApiParam("page", String.valueOf(page))
                .build();
    }

    @Override
    public Type getReturnType() {
        return String.class;
    }
}
