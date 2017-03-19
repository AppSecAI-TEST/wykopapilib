package wykopapi.examples;

import wykopapi.api.dto.Entry;
import wykopapi.api.executor.RequestExecutor;
import wykopapi.api.properties.PropertiesService;
import wykopapi.api.properties.SimplePropertiesService;
import wykopapi.api.request.entries.GetEntryRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class SaveVotersAvatars {
    public static void main(String[] args) throws IOException {
        PropertiesService propertiesService = new SimplePropertiesService
                .Builder("appkey", "secret").build();
        RequestExecutor executor = new RequestExecutor(propertiesService);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Entry id: ");
        int entryId = scanner.nextInt();

        String directoryName = "AVATARS_" + entryId + "_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyHHmmss")) +
                File.separator;

        Files.createDirectories(Paths.get(directoryName));

        System.out.println("Downloading entry data...");

        GetEntryRequest getEntryRequest = GetEntryRequest.builder(entryId).clearOutput(true).build();
        Entry entry = executor.execute(getEntryRequest).orElseThrow(() -> new RuntimeException("Could not download entry"));

        System.out.println("Downloading images...");

        entry.getVoters().forEach(voter -> {
            String imageUrl = voter.getAuthorAvatarBig();
            String filename = directoryName + voter.getAuthor() + imageUrl.substring(imageUrl.lastIndexOf("."));
            try(InputStream in = new URL(imageUrl).openStream()){
                Files.copy(in, Paths.get(filename));
            } catch (IOException e) {
                System.out.println("Could not download image: " + voter.getAuthor());
            }
        });

        System.out.println("Done");
    }
}
