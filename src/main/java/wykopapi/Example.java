package wykopapi;

import wykopapi.examples.SaveVotersAvatars;
import wykopapi.executor.RequestExecutor;
import wykopapi.properties.FilePropertiesService;
import wykopapi.properties.PropertiesService;

import java.io.IOException;
import java.util.Scanner;


public class Example {
    public static void main(String[] args) throws IOException {
        PropertiesService propertiesService = new FilePropertiesService("src/main/resources/application.properties");
        RequestExecutor executor = new RequestExecutor(propertiesService);

        SaveVotersAvatars saveVotersAvatars = new SaveVotersAvatars();

        System.out.print("Entry id: ");
        int entryId = new Scanner(System.in).nextInt();

        saveVotersAvatars.run(executor, entryId);
    }
}
