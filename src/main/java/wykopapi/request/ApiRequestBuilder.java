package wykopapi.request;

public interface ApiRequestBuilder<T extends ApiRequest> {
    T build();
}
