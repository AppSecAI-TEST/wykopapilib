package wykopapi.api.request.link;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;

// TODO proper return type
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CancelLinkRequest implements ApiRequest<String> {
    private final String userKey;
    private final int linkId;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("link", "cancel")
                .addMethodParam(String.valueOf(linkId))
                .addApiParam("userkey", userKey)
                .build();
    }

    @Override
    public Type getReturnType() {
        return String.class;
    }

    public static CancelLinkRequestBuilder builder(@NotNull String userKey, int linkId) {
        return new CancelLinkRequestBuilder(userKey, linkId);
    }

    public static class CancelLinkRequestBuilder {
        private String userKey;
        private int linkId;

        private CancelLinkRequestBuilder(@NotNull String userKey, int linkId) {
            if (Strings.isNullOrEmpty(userKey)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            if (linkId < 0) {
                throw new IllegalArgumentException("Parameter cannot be negative");
            }
            this.userKey = userKey;
            this.linkId = linkId;
        }

        public CancelLinkRequest build() {
            return new CancelLinkRequest(userKey, linkId);
        }
    }
}
