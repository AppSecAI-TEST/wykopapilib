package wykopapi.api.request.pm;

import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.api.request.AbstractRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;

// TODO check return type
public class DeleteConversationRequest extends AbstractRequest<Boolean> {
    private final String userKey;
    private final String userName;

    private DeleteConversationRequest(String userKey, String userName) {
        this.userKey = userKey;
        this.userName = userName;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("pm").addPathSegment("deleteconversation")
                .addEncodedPathSegment(userName)
                .addPathSegment("userkey").addEncodedPathSegment(userKey)
                .build();

        return new Request.Builder()
                .url(url).get()
                .build();
    }

    @Override
    public Type getReturnType() {
        return Boolean.class;
    }

    public static class Builder implements ApiRequestBuilder<DeleteConversationRequest> {
        private String userKey;
        private String userName;

        public Builder(String userKey, String userName) {
            this.userKey = userKey;
            this.userName = userName;
        }

        @Override
        public DeleteConversationRequest build() {
            return new DeleteConversationRequest(userKey, userName);
        }
    }
}
