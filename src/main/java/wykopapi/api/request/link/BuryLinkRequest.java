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
public class BuryLinkRequest implements ApiRequest<String> {
    private final String userKey;
    private final int linkId;
    private final int buryReasonId; // TODO ???

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("link", "dig")
                .addMethodParam(String.valueOf(linkId))
                .addMethodParam(String.valueOf(buryReasonId))
                .addApiParam("userkey", userKey)
                .build();
    }

    @Override
    public Type getReturnType() {
        return String.class;
    }

    public static BuryLinkRequestBuilder builder(@NotNull String userKey, int linkId, int buryReasonId) {
        return new BuryLinkRequestBuilder(userKey, linkId, buryReasonId);
    }

    public static class BuryLinkRequestBuilder {
        private String userKey;
        private int linkId;
        private int buryReasonId;

        private BuryLinkRequestBuilder(@NotNull String userKey, int linkId, int buryReasonId) {
            if (Strings.isNullOrEmpty(userKey)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            if (linkId < 0 || buryReasonId < 0) {
                throw new IllegalArgumentException("Parameters cannot be negative");
            }
            this.userKey = userKey;
            this.linkId = linkId;
            this.buryReasonId = buryReasonId;
        }

        public BuryLinkRequest build() {
            return new BuryLinkRequest(userKey, linkId, buryReasonId);
        }
    }
}
