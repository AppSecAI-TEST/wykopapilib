package wykopapi.api;

public final class ApiFactory {
    private final ApiHelper apiHelper;

    public ApiFactory(String appKey, String secret) {
        this.apiHelper = new ApiHelper(appKey, secret);
    }

    public ProfileApi getProfileApi() {
        return new ProfileApi(apiHelper);
    }

    public StreamApi getStreamApi() {
        return new StreamApi(apiHelper);
    }

    public UserApi getUserApi() {
        return new UserApi(apiHelper);
    }

    public EntriesApi getEntriesApi() {
        return new EntriesApi(apiHelper);
    }
}
