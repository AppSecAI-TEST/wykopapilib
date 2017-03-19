package wykopapi.api.request.pm;

import com.google.common.base.Strings;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import wykopapi.api.dto.PmMessage;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class GetConversationRequest implements ApiRequest<List<PmMessage>> {
    private final String userKey;
    private final String userName;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("pm", "conversation")
                .addMethodParam(userName)
                .addApiParam("userkey", userKey)
                .build();
    }

    @Override
    public Type getReturnType() {
        return new TypeToken<List<PmMessage>>(){}.getType();
    }

    public static GetConversationRequestBuilder builder(@NotNull String userKey, @NotNull String userName) {
        return new GetConversationRequestBuilder(userKey, userName);
    }

    public static class GetConversationRequestBuilder {
        private String userKey;
        private String userName;

        private GetConversationRequestBuilder(@NotNull String userKey, @NotNull String userName) {
            if (Strings.isNullOrEmpty(userKey) || Strings.isNullOrEmpty(userName)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            this.userKey = userKey;
            this.userName = userName;
        }

        public GetConversationRequest build() {
            return new GetConversationRequest(userKey, userName);
        }
    }
}
