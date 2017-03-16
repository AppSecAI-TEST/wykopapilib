package wykopapi.api.request.pm;

import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.api.dto.PmMessage;
import wykopapi.api.request.AbstractRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.List;

public final class GetConversationRequest extends AbstractRequest<List<PmMessage>> {
    private final String userKey;
    private final String userName;

    private GetConversationRequest(String userKey, String userName) {
        this.userKey = userKey;
        this.userName = userName;
    }

    @Override
    public Request getRequest() {
        HttpUrl url = newUrlBuilder()
                .addPathSegment("pm").addPathSegment("conversation")
                .addEncodedPathSegment(userName)
                .addPathSegment("userkey").addEncodedPathSegment(userKey)
                .build();

        return new Request.Builder()
                .url(url).get()
                .build();
    }

    @Override
    public Type getReturnType() {
        return new TypeToken<List<PmMessage>>(){}.getType();
    }

    public static class Builder implements ApiRequestBuilder<GetConversationRequest> {
        private String userKey;
        private String userName;

        public Builder(String userKey, String userName) {
            this.userKey = userKey;
            this.userName = userName;
        }

        @Override
        public GetConversationRequest build() {
            return new GetConversationRequest(userKey, userName);
        }
    }
}
