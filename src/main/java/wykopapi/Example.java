package wykopapi;

import wykopapi.api.ApiFactory;
import wykopapi.api.EntriesApi;
import wykopapi.api.StreamApi;
import wykopapi.api.UserApi;
import wykopapi.dto.Entry;
import wykopapi.dto.Profile;

import java.util.List;

public class Example {
    public static void main(String[] args) {
        ApiProperties apiProperties = new ApiProperties();
        ApiFactory apiFactory = new ApiFactory(apiProperties.getAppKey(), apiProperties.getSecret());

        UserApi userApi = apiFactory.getUserApi();
        EntriesApi entriesApi = apiFactory.getEntriesApi();
        StreamApi streamApi = apiFactory.getStreamApi();

        Profile profile = userApi.login(apiProperties.getAccountKey())
                .orElseThrow(() -> new RuntimeException("login failed"));
        List<Entry> entryList = streamApi.getEntries(1)
                .orElseThrow(() -> new RuntimeException("Could not get entries"));
        for (Entry entry : entryList) {
            entriesApi.addEntryComment("chciałem być marynarzem, chciałem pisać komentarze", entry.getId(), profile.getUserkey())
                    .ifError(error -> System.out.println("coś się spierdoliło - " + error.getMessage()));
        }
    }
}
