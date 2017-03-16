package wykopapi.examples;

import wykopapi.api.dto.Profile;
import wykopapi.api.executor.RequestExecutor;
import wykopapi.api.properties.FilePropertiesService;
import wykopapi.api.properties.PropertiesService;
import wykopapi.api.request.entries.AddEntryRequest;
import wykopapi.api.request.entries.GetEntryRequest;
import wykopapi.api.request.user.LoginRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Turn this on and go out for a few hours
 */
public class AfkShitstorm {

    public static void main(String[] args) throws IOException, InterruptedException {
        PropertiesService propertiesService =
                new FilePropertiesService("src/main/resources/application.properties");
        RequestExecutor executor = new RequestExecutor(propertiesService);

        LoginRequest loginRequest = new LoginRequest.Builder(propertiesService.getAccountKey()).build();
        Profile profile = executor.execute(loginRequest)
                .orElseThrow(RuntimeException::new);

        String[] texts = {
                "Kobieta kończy się na 50 kg",
                "Mężczyzna zaczyna się od 180 cm",
                "po ile cebula w biedronce? #cebuladeals",
                "mój kot przeszedł po klawiaturze i dostał sraczki, wołam #programowanie bo wy często używacie klawiatury, może wiecie czemu",
                "zrobiłbym #rozdajo ale nawet nie wiem co xd",
                "tylko pedały kupują ajfony i macbooki, prawdziwi mężczyźni mają androida #ios #android",
                "haha androidy mają tylko biedaki których nie stać na ajfona #ios #android"
        };

        Random random = new Random();
        List<Integer> entriesIds = new ArrayList<>();

        while (true) {
            int minutes = random.nextInt(30) + 30;
            int text = random.nextInt(texts.length);

            if (entriesIds.size() > 0) {
                entriesIds.forEach(id -> {
                    GetEntryRequest getEntryRequest = new GetEntryRequest
                            .Builder(id).build();
                    executor.execute(getEntryRequest)
                            .ifSuccess(entry -> {
                                System.out.println("Wpis: " + entry.getBody());
                                System.out.println("Ilość plusów: " + entry.getVoteCount());
                                System.out.println("Ilość komentarzy: " + entry.getCommentCount());
                            });
                });
            }

            TimeUnit.MINUTES.sleep(minutes);

            AddEntryRequest addEntryRequest = new AddEntryRequest
                    .Builder(profile.getUserkey())
                    .setBody(texts[text])
                    .build();

            executor.execute(addEntryRequest)
                    .ifSuccess(e -> {
                        System.out.println("Added: " + texts[text]);
                        entriesIds.add(e.getId());
                    })
                    .ifError(System.out::println);
        }
    }
}
