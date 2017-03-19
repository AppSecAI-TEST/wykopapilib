package wykopapi.api.extractor;

import okhttp3.MultipartBody;
import okio.Buffer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MultipartParameterExtractor implements ParameterExtractor<MultipartBody> {
    private static final Pattern pattern = Pattern.compile(" name=\"([^\"]*)\"");
    @Override
    public Map<String, String> extract(MultipartBody requestBody) {
        Map<String, String> parameters = new HashMap<>();
        requestBody.parts().forEach(part -> {
            Matcher matcher = pattern.matcher(part.headers().get("Content-Disposition"));
            if (matcher.find()) {
                String key = matcher.group(1);
                if (key.equals("embed")) return;
                try {
                    String value = extractValue(part);
                    parameters.put(key, value);
                } catch (IOException ignored) {
                    // ignore
                }
            }
        });
        return parameters;
    }

    private String extractValue(MultipartBody.Part part) throws IOException {
        Buffer buffer = new Buffer();
        part.body().writeTo(buffer);
        return buffer.readUtf8();
    }
}