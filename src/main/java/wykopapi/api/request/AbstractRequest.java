package wykopapi.api.request;

import okhttp3.*;

import java.io.File;
import java.util.Map;

public abstract class AbstractRequest<T> implements ApiRequest<T> {
    private static final String SCHEME = "https";
    private static final String HOST = "a.wykop.pl";

    protected AbstractRequest() {
    }

    @Override
    public abstract Request getRequest();

    protected HttpUrl.Builder newUrlBuilder() {
        return new HttpUrl.Builder()
                .scheme(SCHEME)
                .host(HOST);
    }

    protected FormBody createBodyFromParams(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        params.forEach(builder::addEncoded);
        return builder.build();
    }

    protected MultipartBody createMultipartBody(Map<String, String> params, File file) {
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        params.forEach(bodyBuilder::addFormDataPart);
        bodyBuilder.addFormDataPart("embed", "image.jpg",
                RequestBody.create(MediaType.parse("image/jpeg"), file));

        return bodyBuilder.build();
    }
}
