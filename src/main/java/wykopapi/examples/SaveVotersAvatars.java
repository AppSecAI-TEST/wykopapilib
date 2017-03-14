package wykopapi.examples;

import wykopapi.dto.Entry;
import wykopapi.executor.RequestExecutor;
import wykopapi.properties.PropertiesService;
import wykopapi.properties.PropertiesServiceFactory;
import wykopapi.request.entries.GetEntryRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class SaveVotersAvatars {
    public static void main(String[] args) {
        PropertiesService propertiesService = PropertiesServiceFactory.getPropertiesService();
        RequestExecutor requestExecutor = new RequestExecutor(propertiesService);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj identyfikator wpisu: ");
        int entryId = scanner.nextInt();
        String directoryName = "AVATARS_" + entryId + File.separator;
        try {
            Files.createDirectory(Paths.get(directoryName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        GetEntryRequest getEntryRequest = new GetEntryRequest.Builder(entryId).setClearOutput(true).build();
        Entry entry = requestExecutor.execute(getEntryRequest).orElseThrow(() -> new RuntimeException("Could not download entry"));

        entry.getVoters().forEach(voter -> {
            String imageUrl = voter.getAuthorAvatarBig();
            String filename = directoryName + voter.getAuthor() + imageUrl.substring(imageUrl.lastIndexOf("."));
            try(InputStream in = new URL(imageUrl).openStream()){
                Files.copy(in, Paths.get(filename));
            } catch (IOException e) {
                System.out.println("Culd not download image: " + voter.getAuthor());
            }
        });
    }
}
