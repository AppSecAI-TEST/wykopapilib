package wykopapi.api.request.link;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import wykopapi.api.dto.Link;
import wykopapi.api.request.ApiRequest;
import wykopapi.api.request.ApiRequestBuilder;

import java.lang.reflect.Type;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LinkIndexRequest implements ApiRequest<Link> {
    private final String userKey;
    private final int linkId;

    @Override
    public Request getRequest() {
        return new ApiRequestBuilder("link", "index")
                .addMethodParam(String.valueOf(linkId))
                .addApiParam("userkey", userKey)
                .build();
    }

    @Override
    public Type getReturnType() {
        return Link.class;
    }

    public static LinkIndexRequestBuilder builder(@NotNull String userKey, int linkId) {
        return new LinkIndexRequestBuilder(userKey, linkId);
    }

    public static class LinkIndexRequestBuilder {
        private String userKey;
        private int linkId;

        private LinkIndexRequestBuilder(@NotNull String userKey, int linkId) {
            if (Strings.isNullOrEmpty(userKey)) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            if (linkId < 0) {
                throw new IllegalArgumentException("Parameter cannot be negative");
            }
            this.userKey = userKey;
            this.linkId = linkId;
        }

        public LinkIndexRequest build() {
            return new LinkIndexRequest(userKey, linkId);
        }
    }
}
