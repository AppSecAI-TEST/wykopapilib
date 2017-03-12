package wykopapi.api;

import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.Request;
import wykopapi.dto.Entry;
import wykopapi.dto.EntryComment;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public final class ProfileApi {
    private final ApiHelper helper;

    ProfileApi(ApiHelper apiHelper) {
        this.helper = apiHelper;
    }

    public Result<List<Entry>> getUserEntries(String user, int page) {
        HttpUrl url = helper.getUrlBuilder().addPathSegment("profile")
                .addPathSegment("entries")
                .addEncodedPathSegment(user)
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

    public Result<List<EntryComment>> getUserComments(String user, int page) {
        HttpUrl url = helper.getUrlBuilder().addPathSegment("profile")
                .addPathSegment("entriescomments")
                .addEncodedPathSegment(user)
                .addPathSegment("appkey").addEncodedPathSegment(helper.getAppKey())
                .addPathSegment("page").addEncodedPathSegment(Integer.toString(page))
                .addPathSegment("output").addPathSegment("clear")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("apisign", helper.getApiSign(url, Collections.emptyMap()))
                .get()
                .build();
        return helper.execute(request, new TypeToken<List<EntryComment>>(){});
    }
}
