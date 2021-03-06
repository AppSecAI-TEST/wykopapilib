package wykopapi.examples;

import wykopapi.api.dto.Entry;
import wykopapi.api.executor.RequestExecutor;
import wykopapi.api.properties.FilePropertiesService;
import wykopapi.api.properties.PropertiesService;
import wykopapi.api.request.entries.FavoriteEntryRequest;
import wykopapi.api.request.entries.VoteEntryRequest;
import wykopapi.api.request.rank.RankIndexRequest;
import wykopapi.api.request.stream.StreamIndexRequest;
import wykopapi.api.request.tag.TagIndexRequest;
import wykopapi.api.request.tags.TagsIndexRequest;
import wykopapi.api.request.user.LoginRequest;

import java.io.IOException;
import java.util.List;

public class VoteBot {
    public static void main(String[] args) throws IOException {
        PropertiesService propertiesService = new FilePropertiesService("src/main/resources/application.properties");
        RequestExecutor executor = new RequestExecutor(propertiesService);

        LoginRequest loginRequest = LoginRequest.builder(propertiesService.getAccountKey()).build();
        String userKey = executor.execute(loginRequest)
                .ifError(System.out::println)
                .orElseThrow(RuntimeException::new)
                .getUserkey();

        RankIndexRequest request = RankIndexRequest.builder().build();
        executor.execute(request).ifSuccess(System.out::println);

//        StreamIndexRequest streamIndexRequest = StreamIndexRequest.builder().build();
//        List<Entry> entries = executor.execute(streamIndexRequest).get();
//
//        entries.forEach(e -> {
//            VoteEntryRequest voteEntryRequest = VoteEntryRequest.builder(userKey, e.getId()).build();
//            executor.execute(voteEntryRequest)
//                    .ifSuccess(System.out::println)
//                    .ifError(System.out::println);
//        });
    }
}
