package wykopapi.api;

import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.dto.Entry;

import java.util.Collections;
import java.util.List;

public final class StreamApi {
    private final ApiHelper helper;

    StreamApi(ApiHelper apiHelper) {
        this.helper = apiHelper;
    }

    public Result<List<Entry>> getEntries(int page) {
        HttpUrl url = helper.getUrlBuilder().addPathSegment("stream")
                .addPathSegment("index")
                .addPathSegment("appkey").addEncodedPathSegment(helper.getAppKey())
                .addPathSegment("page").addEncodedPathSegment(Integer.toString(page))
                .addPathSegment("output").addPathSegment("clear")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("apisign", helper.getApiSign(url, Collections.emptyMap()))
                .get()
                .build();

        return helper.execute(request, new TypeToken<List<Entry>>(){});
    }

    public Result<List<Entry>> getHotEntries(int page, int period) {
        HttpUrl url = helper.getUrlBuilder().addPathSegment("stream")
                .addPathSegment("hot")
                .addPathSegment("appkey").addEncodedPathSegment(helper.getAppKey())
                .addPathSegment("page").addEncodedPathSegment(Integer.toString(page))
                .addPathSegment("period").addEncodedPathSegment(Integer.toString(period))
                .addPathSegment("output").addPathSegment("clear")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("apisign", helper.getApiSign(url, Collections.emptyMap()))
                .get()
                .build();

        return helper.execute(request, new TypeToken<List<Entry>>(){});
    }
}
