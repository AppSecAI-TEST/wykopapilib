package wykopapi;

import wykopapi.dto.Profile;
import wykopapi.executor.RequestExecutor;
import wykopapi.properties.FilePropertiesService;
import wykopapi.properties.PropertiesService;
import wykopapi.request.ApiRequest;
import wykopapi.request.entries.*;
import wykopapi.request.user.LoginRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Example {
    public static void main(String[] args) {
        PropertiesService propertiesService = new FilePropertiesService("src/main/resources/application.properties");
        RequestExecutor executor = new RequestExecutor(propertiesService);

        int last = 1;
        while (true) {
            LocalDateTime dateTime = LocalDateTime.now();
            if (dateTime.getHour() != last/* && dateTime.getMinute() == 0 && dateTime.getSecond() == 0*/) {
                ApiRequest<Profile> userLoginRequest = new LoginRequest.Builder(propertiesService.getAccountKey())
                        .build();
                Profile profile = executor.execute(userLoginRequest).orElseThrow(() -> new RuntimeException("RIP"));
                AddEntryRequest addEntryRequest = new AddEntryRequest
                        .Builder(profile.getUserkey(),
                        dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " #listaobecnosci")
                        .build();
                executor.execute(addEntryRequest)
                        .ifSuccess(entry -> System.out.println("ok"))
                        .ifError(System.out::println);
                last = dateTime.getHour();
            }
        }
    }
}
