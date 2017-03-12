package wykopapi;

import wykopapi.dto.AddEntry;
import wykopapi.properties.PropertiesServiceFactory;
import wykopapi.request.ApiRequest;
import wykopapi.request.entries.EntriesAddRequest;
import wykopapi.request.user.UserLoginRequest;
import wykopapi.dto.Profile;


public class Example {
    public static void main(String[] args) {
        RequestExecutor executor = new RequestExecutor();
        ApiRequest<Profile> userLoginRequest = new UserLoginRequest.Builder()
                .setAccountKey(PropertiesServiceFactory.getPropertiesService().getAccountKey())
                .build();
        executor.execute(userLoginRequest).ifSuccess(profile -> {
            ApiRequest<AddEntry> addEntryRequest =
                    new EntriesAddRequest.Builder(
                            "słucham psa jak gra",
                            profile.getUserkey()).build();
            executor.execute(addEntryRequest).ifSuccess(System.out::println);
        });
    }
}
