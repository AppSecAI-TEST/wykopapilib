package wykopapi.api.request.pm;

import com.google.common.base.Strings;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import wykopapi.api.dto.Conversation;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ListConversationsRequest implements ApiRequest<List<Conversation>> {
    private final String userKey;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("pm", "conversationslist")
                .addApiParam("userkey", userKey)
                .build();
    }

    @Override
    public Type getReturnType() {
        return new TypeToken<List<Conversation>>(){}.getType();
    }

    public static ListConversationsRequestBuilder builder(@NotNull String userKey) {
        return new ListConversationsRequestBuilder(userKey);
    }

    public static class ListConversationsRequestBuilder {
        private String userKey;

        private ListConversationsRequestBuilder(@NotNull String userKey) {
            if (Strings.isNullOrEmpty(userKey)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            this.userKey = userKey;
        }

        public ListConversationsRequest build() {
            return new ListConversationsRequest(userKey);
        }
    }
}
