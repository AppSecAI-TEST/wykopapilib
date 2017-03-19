package wykopapi.api.request.pm;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;

// TODO check return type
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DeleteConversationRequest implements ApiRequest<Boolean> {
    private final String userKey;
    private final String userName;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("pm", "deleteconversation")
                .addMethodParam(userName)
                .addApiParam("userkey", userKey)
                .build();
    }

    @Override
    public Type getReturnType() {
        return Boolean.class;
    }

    public static DeleteConversationRequestBuilder builder(@NotNull String userKey, @NotNull String userName) {
        return new DeleteConversationRequestBuilder(userKey, userName);
    }

    public static class DeleteConversationRequestBuilder {
        private String userKey;
        private String userName;

        private DeleteConversationRequestBuilder(@NotNull String userKey, @NotNull String userName) {
            if (Strings.isNullOrEmpty(userKey) || Strings.isNullOrEmpty(userName)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            this.userKey = userKey;
            this.userName = userName;
        }

        public DeleteConversationRequest build() {
            return new DeleteConversationRequest(userKey, userName);
        }
    }
}
