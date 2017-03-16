package wykopapi.api.request.pm;

import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.api.dto.Conversation;
import wykopapi.api.request.AbstractRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.List;

public class ListConversationsRequest extends AbstractRequest<List<Conversation>> {
    private final String userKey;

    private ListConversationsRequest(String userKey) {
        this.userKey = userKey;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("pm").addPathSegment("conversationslist")
                .addPathSegment("userkey").addEncodedPathSegment(userKey)
                .build();

        return new Request.Builder()
                .url(url).get()
                .build();
    }

    @Override
    public Type getReturnType() {
        return new TypeToken<List<Conversation>>(){}.getType();
    }

    public static class Builder implements ApiRequestBuilder<ListConversationsRequest> {
        private String userKey;

        public Builder(String userKey) {
            this.userKey = userKey;
        }

        @Override
        public ListConversationsRequest build() {
            return new ListConversationsRequest(userKey);
        }
    }
}
