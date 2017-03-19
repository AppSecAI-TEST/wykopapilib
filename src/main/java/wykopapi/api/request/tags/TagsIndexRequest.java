package wykopapi.api.request.tags;

import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import wykopapi.api.dto.Tag;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class TagsIndexRequest implements ApiRequest<List<Tag>> {
    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("tags", "index").build();
    }

    @Override
    public Type getReturnType() {
        return new TypeToken<List<Tag>>(){}.getType();
    }

    public static TagsIndexRequestBuilder builder() {
        return new TagsIndexRequestBuilder();
    }

    public static class TagsIndexRequestBuilder {
        private TagsIndexRequestBuilder() {
        }

        public TagsIndexRequest build() {
            return new TagsIndexRequest();
        }
    }
}
