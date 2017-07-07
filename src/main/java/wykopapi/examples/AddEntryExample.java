package wykopapi.examples;

import wykopapi.api.dto.IdResult;
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

        LoginRequest loginRequest = LoginRequest.builder(propertiesService.getAccountKey()).build();
        Profile profile = executor.execute(loginRequest)
                .orElseThrow(RuntimeException::new);

        AddEntryRequest addEntryRequest = AddEntryRequest.builder(profile.getUserkey())
                .body("slucham psa jak gra")
                .embedFile(new File("src/main/resources/dogpiano.jpg"))
                .build();
        IdResult idResult = executor.execute(addEntryRequest)
                .ifSuccess(e -> System.out.println(e.getId()))
                .ifError(System.out::println)
                .get();

        AddEntryCommentRequest addEntryCommentRequest = AddEntryCommentRequest
                .builder(profile.getUserkey(), idResult.getId())
                .body("@" + profile.getLogin() + ": ja też xd")
                .embedFile(new File("src/main/resources/dogpiano.jpg"))
                .build();

        executor.execute(addEntryCommentRequest)
                .ifSuccess(e -> System.out.println(e.getId()))
                .ifError(System.out::println);

    }
}