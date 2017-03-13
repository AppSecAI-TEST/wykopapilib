package wykopapi.request.tags;

import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.dto.Tag;
import wykopapi.request.AbstractRequest;
import wykopapi.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.List;

public class TagsIndexRequest extends AbstractRequest<List<Tag>> {
    private TagsIndexRequest() {
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("tags").addPathSegment("index")
                .build();

        return new Request.Builder()
                .url(url).get()
                .build();
    }

    @Override
    public Type getReturnType() {
        return new TypeToken<List<Tag>>(){}.getType();
    }

    public static class Builder implements ApiRequestBuilder<TagsIndexRequest> {
        @Override
        public TagsIndexRequest build() {
            return new TagsIndexRequest();
        }
    }
}
