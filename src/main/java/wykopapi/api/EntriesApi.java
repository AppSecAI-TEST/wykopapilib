package wykopapi.api;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import wykopapi.dto.AddEntry;
import wykopapi.dto.Entry;
import wykopapi.dto.VoteEntry;

import java.util.Collections;

public final class EntriesApi {
    private final ApiHelper helper;

    EntriesApi(ApiHelper apiHelper) {
        this.helper = apiHelper;
    }

    public Result<Entry> getEntry(int entryId) {
        HttpUrl url = helper.getUrlBuilder().addPathSegment("entries")
                .addPathSegment("index").addEncodedPathSegment(Integer.toString(entryId))
                .addPathSegment("appkey").addEncodedPathSegment(helper.getAppKey())
                .addPathSegment("output").addPathSegment("clear")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("apisign", helper.getApiSign(url, Collections.emptyMap()))
                .get()
                .build();

        return helper.execute(request, Entry.class);
    }

    public Result<AddEntry> addEntry(String body, String userKey) {
        HttpUrl url = helper.getUrlBuilder().addPathSegment("entries")
                .addPathSegment("add")
                .addPathSegment("userkey").addEncodedPathSegment(userKey)
                .addPathSegment("appkey").addEncodedPathSegment(helper.getAppKey())
                .build();

        FormBody formBody = new FormBody.Builder().addEncoded("body", body).build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("apisign", helper.getApiSign(url, Collections.singletonMap("body", body)))
                .post(formBody)
                .build();

        return helper.execute(request, AddEntry.class);
    }

    public Result<AddEntry> addEntryComment(String body, int entryId, String userKey) {
        HttpUrl url = helper.getUrlBuilder().addPathSegment("entries")
                .addPathSegment("addcomment").addEncodedPathSegment(Integer.toString(entryId))
                .addPathSegment("userkey").addEncodedPathSegment(userKey)
                .addPathSegment("appkey").addEncodedPathSegment(helper.getAppKey())
                .build();

        FormBody formBody = new FormBody.Builder().addEncoded("body", body).build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("apisign", helper.getApiSign(url, Collections.singletonMap("body", body)))
                .post(formBody)
                .build();

        return helper.execute(request, AddEntry.class);
    }

    public Result voteEntry(int entryId, String userkey) {
        HttpUrl url = helper.getUrlBuilder().addPathSegment("entries")
                .addPathSegment("vote").addPathSegment("entry")
                .addEncodedPathSegment(Integer.toString(entryId))
                .addPathSegment("userkey").addEncodedPathSegment(userkey)
                .addPathSegment("appkey").addEncodedPathSegment(helper.getAppKey())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("apisign", helper.getApiSign(url, Collections.emptyMap()))
                .get()
                .build();

        return helper.execute(request, VoteEntry.class);
    }

//    public Result voteEntryComment(int entryId, int entryCommentId, String userkey) {
//
//    }
}
