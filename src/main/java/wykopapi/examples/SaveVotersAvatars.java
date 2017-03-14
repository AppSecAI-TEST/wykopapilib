package wykopapi.examples;

import wykopapi.dto.Entry;
import wykopapi.executor.RequestExecutor;
import wykopapi.properties.PropertiesService;
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
        Scanner scanner = new Scanner(System.in);
        System.out.print("App Key: ");
        String appKey = scanner.next();
        System.out.print("Secret: ");
        String secret = scanner.next();
        System.out.println("Entry id: ");
        int entryId = scanner.nextInt();

        PropertiesService propertiesService = new PropertiesService() {
            @Override
            public String getAppKey() {
                return appKey;
            }

            @Override
            public String getSecret() {
                return secret;
            }

            @Override
            public String getAccountKey() {
                return null;
            }
        };
        RequestExecutor requestExecutor = new RequestExecutor(propertiesService);

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
                System.out.println("Could not download image: " + voter.getAuthor());
            }
        });
    }
}
