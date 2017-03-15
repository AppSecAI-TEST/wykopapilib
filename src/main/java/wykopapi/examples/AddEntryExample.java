package wykopapi.examples;

import wykopapi.api.dto.EntryOperation;
import wykopapi.api.dto.Profile;
import wykopapi.api.executor.RequestExecutor;
import wykopapi.api.properties.FilePropertiesService;
import wykopapi.api.properties.PropertiesService;
import wykopapi.api.request.entries.AddEntryCommentRequest;
import wykopapi.api.request.entries.AddEntryRequest;
import wykopapi.api.request.user.LoginRequest;

import java.io.File;
import java.io.IOException;

public class AddEntryExample {
    public static void main(String[] args) throws IOException {
        PropertiesService propertiesService =
                new FilePropertiesService("src/main/resources/application.properties");
        RequestExecutor executor = new RequestExecutor(propertiesService);

        LoginRequest loginRequest = new LoginRequest.Builder(propertiesService.getAccountKey()).build();
        Profile profile = executor.execute(loginRequest)
                .orElseThrow(RuntimeException::new);

        AddEntryRequest addEntryRequest = new AddEntryRequest
                .Builder(profile.getUserkey(), "słucham psa jak gra")
                .setEmbedFile(new File("src/main/resources/dogpiano.jpg"))
                .build();
        EntryOperation entryOperation = executor.execute(addEntryRequest)
                .ifSuccess(e -> System.out.println(e.getId()))
                .ifError(System.out::println)
                .get();

        AddEntryCommentRequest addEntryCommentRequest = new AddEntryCommentRequest
                .Builder(profile.getUserkey(), entryOperation.getId(),
                "@" + profile.getLogin() + ": ja też xd")
                .setEmbedFile(new File("src/main/resources/dogpiano.jpg"))
                .build();

        executor.execute(addEntryCommentRequest)
                .ifSuccess(e -> System.out.println(e.getId()))
                .ifError(System.out::println);
    }
}
