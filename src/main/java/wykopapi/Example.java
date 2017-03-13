package wykopapi;

import wykopapi.dto.EntryOperation;
import wykopapi.dto.Profile;
import wykopapi.executor.RequestExecutor;
import wykopapi.properties.PropertiesService;
import wykopapi.properties.PropertiesServiceFactory;
import wykopapi.request.ApiRequest;
import wykopapi.request.entries.AddEntryRequest;
import wykopapi.request.entries.DeleteEntryRequest;
import wykopapi.request.entries.EditEntryRequest;
import wykopapi.request.entries.GetEntryRequest;
import wykopapi.request.user.LoginRequest;

import java.util.concurrent.TimeUnit;


public class Example {
    public static void main(String[] args) {
        PropertiesService propertiesService = PropertiesServiceFactory.getPropertiesService();
        RequestExecutor executor = new RequestExecutor(propertiesService);

        ApiRequest<Profile> userLoginRequest = new LoginRequest.Builder(propertiesService.getAccountKey())
                .build();
        Profile profile = executor.execute(userLoginRequest).orElseThrow(() -> new RuntimeException("RIP"));

        AddEntryRequest addEntryRequest = new AddEntryRequest.Builder(profile.getUserkey(), "test 123")
                .build();
        EntryOperation entryOperation = executor.execute(addEntryRequest).orElseThrow(() -> new RuntimeException("RIP"));

        DeleteEntryRequest deleteEntryRequest = new DeleteEntryRequest.Builder(profile.getUserkey(), entryOperation.getId())
                .build();
        executor.execute(deleteEntryRequest).ifSuccess(System.out::println).ifError(System.out::println);
    }
}
