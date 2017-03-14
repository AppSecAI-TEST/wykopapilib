package wykopapi.api.extractor;

import okhttp3.RequestBody;

import java.util.Map;

public interface ParameterExtractor<T extends RequestBody> {
    Map<String, String> extract(T requestBody);
}
