package wykopapi.extractor;

import okhttp3.FormBody;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class FormParameterExtractor implements ParameterExtractor<FormBody> {

    @Override
    public Map<String, String> extract(FormBody requestBody) {
        Map<String, String> parameters = new HashMap<>();
        for (int i = 0; i < requestBody.size(); i++) {
            parameters.put(requestBody.name(i), requestBody.value(i));
        }
        return Collections.unmodifiableMap(parameters);
    }
}
