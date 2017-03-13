package wykopapi;

import wykopapi.dto.Entry;
import wykopapi.dto.EntryOperation;
import wykopapi.dto.Profile;
import wykopapi.executor.RequestExecutor;
import wykopapi.properties.PropertiesService;
import wykopapi.properties.PropertiesServiceFactory;
import wykopapi.request.ApiRequest;
import wykopapi.request.entries.*;
import wykopapi.request.stream.StreamIndexRequest;
import wykopapi.request.user.LoginRequest;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class Example {
    public static void main(String[] args) {
        PropertiesService propertiesService = PropertiesServiceFactory.getPropertiesService();
        RequestExecutor executor = new RequestExecutor(propertiesService);

        ApiRequest<Profile> userLoginRequest = new LoginRequest.Builder(propertiesService.getAccountKey())
                .build();
        Profile profile = executor.execute(userLoginRequest).orElseThrow(() -> new RuntimeException("RIP"));

        StreamIndexRequest streamIndexRequest = new StreamIndexRequest.Builder().build();
        List<Entry> entries = executor.execute(streamIndexRequest).get();

        Entry randomEntry = entries.stream().findAny().get();
        FavoriteEntryRequest favoriteEntryRequest = new FavoriteEntryRequest
                .Builder(profile.getUserkey(), randomEntry.getId())
                .build();

        executor.execute(favoriteEntryRequest)
                .ifSuccess(System.out::println)
                .ifError(System.out::println);
    }
}
