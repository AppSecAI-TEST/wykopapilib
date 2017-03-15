package wykopapi.examples;

import wykopapi.api.executor.RequestExecutor;
import wykopapi.api.properties.FilePropertiesService;
import wykopapi.api.properties.PropertiesService;
import wykopapi.api.request.entries.AddEntryRequest;
import wykopapi.api.request.user.LoginRequest;

import java.io.IOException;

public class AddEntryExample {
    public static void main(String[] args) throws IOException {
        PropertiesService propertiesService =
                new FilePropertiesService("src/main/resources/application.properties");
        RequestExecutor executor = new RequestExecutor(propertiesService);

        LoginRequest loginRequest = new LoginRequest.Builder(propertiesService.getAccountKey()).build();
        String userKey = executor.execute(loginRequest)
                .orElseThrow(RuntimeException::new)
                .getUserkey();

        AddEntryRequest addEntryRequest = new AddEntryRequest
                .Builder(userKey, "słucham psa jak gra")
                .build();
        executor.execute(addEntryRequest)
                .ifSuccess(e -> System.out.println(e.getId()))
                .ifError(System.out::println);
    }
}
