package wykopapi.request;

import okhttp3.Request;

import java.lang.reflect.Type;

public interface ApiRequest<T> {
    Request getRequest();
    Type getReturnType();
}
