package wykopapi.api.extractor;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.util.Collections;
import java.util.Map;

public final class Extractor implements ParameterExtractor<RequestBody> {
    private final ParameterExtractor<FormBody> formBodyParameterExtractor;
    private final ParameterExtractor<MultipartBody> multipartBodyParameterExtractor;

    public Extractor() {
        this.formBodyParameterExtractor = new FormParameterExtractor();
        this.multipartBodyParameterExtractor = new MultipartParameterExtractor();
    }

    @Override
    public Map<String, String> extract(RequestBody requestBody) {
        if (requestBody instanceof FormBody) {
            return formBodyParameterExtractor.extract((FormBody) requestBody);
        }
        else if (requestBody instanceof MultipartBody) {
            return multipartBodyParameterExtractor.extract((MultipartBody) requestBody);
        }
        return Collections.emptyMap();
    }
}
