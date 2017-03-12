package wykopapi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Authentication {
    private final Map<String, String> authParams;

    private Authentication() {
        this.authParams = new HashMap<>();
    }

    public static Authentication of(String accountKey) {
        Authentication authentication = new Authentication();
        authentication.authParams.put("accountkey", accountKey);
        return authentication;
    }

    public static Authentication of(String username, String password) {
        Authentication authentication = new Authentication();
        authentication.authParams.put("username", username);
        authentication.authParams.put("password", password);
        return authentication;
    }

    public static Authentication none() {
        return new Authentication();
    }

    public Map<String, String> getAuthParams() {
        return Collections.unmodifiableMap(authParams);
    }
}
