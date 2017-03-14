package wykopapi.examples;

import wykopapi.dto.Entry;
import wykopapi.executor.RequestExecutor;
import wykopapi.request.entries.GetEntryRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaveVotersAvatars {
    public void run(RequestExecutor requestExecutor, int entryId) {
        String directoryName = "AVATARS_" +
                entryId +
                "_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyHHmmss")) +
                File.separator;

        try {
            Files.createDirectory(Paths.get(directoryName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        System.out.println("Downloading entry data...");

        GetEntryRequest getEntryRequest = new GetEntryRequest.Builder(entryId).setClearOutput(true).build();
        Entry entry = requestExecutor.execute(getEntryRequest).orElseThrow(() -> new RuntimeException("Could not download entry"));

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
