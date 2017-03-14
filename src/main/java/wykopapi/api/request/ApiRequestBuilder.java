package wykopapi.api.request;

public interface ApiRequestBuilder<T extends ApiRequest> {
    T build();
}
