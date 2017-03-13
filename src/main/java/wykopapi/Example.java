package wykopapi;

import wykopapi.dto.Entry;
import wykopapi.dto.Profile;
import wykopapi.executor.RequestExecutor;
import wykopapi.properties.PropertiesService;
import wykopapi.properties.PropertiesServiceFactory;
import wykopapi.request.ApiRequest;
import wykopapi.request.entries.*;
import wykopapi.request.stream.StreamIndexRequest;
import wykopapi.request.user.LoginRequest;

import java.util.List;


public class Example {
    public static void main(String[] args) {
        PropertiesService propertiesService = PropertiesServiceFactory.getPropertiesService();
        RequestExecutor executor = new RequestExecutor(propertiesService);

        ApiRequest<Profile> userLoginRequest = new LoginRequest.Builder(propertiesService.getAccountKey())
                .build();
        Profile profile = executor.execute(userLoginRequest).orElseThrow(() -> new RuntimeException("RIP"));

        StreamIndexRequest streamIndexRequest = new StreamIndexRequest.Builder().build();
        List<Entry> entries = executor.execute(streamIndexRequest).get();

        entries.forEach(entry -> {
            VoteEntryRequest voteEntryRequest = new VoteEntryRequest
                    .Builder(profile.getUserkey(), entry.getId())
                    .build();

            executor.execute(voteEntryRequest)
                    .ifError(error -> System.out.println(error.getMessage()));
        });
    }
}
