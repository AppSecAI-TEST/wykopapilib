package wykopapi;

import wykopapi.dto.AddEntry;
import wykopapi.dto.Profile;
import wykopapi.executor.RequestExecutor;
import wykopapi.properties.PropertiesService;
import wykopapi.properties.PropertiesServiceFactory;
import wykopapi.request.ApiRequest;
import wykopapi.request.entries.EntriesAddRequest;
import wykopapi.request.user.UserLoginRequest;

import java.io.File;


public class Example {
    public static void main(String[] args) {
        PropertiesService propertiesService = PropertiesServiceFactory.getPropertiesService();
        RequestExecutor executor = new RequestExecutor(propertiesService);

        ApiRequest<Profile> userLoginRequest = new UserLoginRequest.Builder(propertiesService.getAccountKey())
                .build();
        Profile profile = executor.execute(userLoginRequest).orElseThrow(() -> new RuntimeException("RIP"));

        ApiRequest<AddEntry> addEntryRequest = new EntriesAddRequest
                .Builder(profile.getUserkey(), "słucham psa jak gra")
                .setEmbedFile(new File("src/main/resources/dogpiano.jpg"))
                .build();
        executor.execute(addEntryRequest)
                .ifSuccess(ok -> System.out.println("OK"))
                .ifError(err -> System.out.println(err.getMessage()));
    }
}
