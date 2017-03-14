package wykopapi.examples;

import wykopapi.api.executor.RequestExecutor;
import wykopapi.api.properties.FilePropertiesService;
import wykopapi.api.properties.PropertiesService;
import wykopapi.api.request.entries.AddEntryRequest;
import wykopapi.api.request.user.LoginRequest;

import java.io.IOException;
import java.time.LocalDateTime;

public class NightShiftXD {
    public static void main(String[] args) throws IOException {
        PropertiesService propertiesService = new FilePropertiesService("src/main/resources/application.properties");
        RequestExecutor executor = new RequestExecutor(propertiesService);

        LoginRequest loginRequest = new LoginRequest.Builder(propertiesService.getAccountKey()).build();
        String userKey = executor.execute(loginRequest)
                .orElseThrow(RuntimeException::new)
                .getUserkey();

        AddEntryRequest addEntryRequest = new AddEntryRequest.Builder(userKey,
                "NOCNA! 01:00:00 #listaobecnosci")
                .build();

        while (true) {
            LocalDateTime dateTime = LocalDateTime.now();
            if (dateTime.getHour() == 1 && dateTime.getMinute() == 0 && dateTime.getSecond() == 0) {
                executor.execute(addEntryRequest)
                        .ifSuccess(e -> System.out.println(e.getId()))
                        .ifError(e -> System.out.println(e.getMessage()));
            }
            else if (dateTime.getHour() == 1) break;
        }
    }
}
