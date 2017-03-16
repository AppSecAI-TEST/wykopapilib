package wykopapi.api.request.user;

import org.junit.Before;
import org.junit.Test;
import wykopapi.api.dto.Profile;
import wykopapi.api.extractor.Extractor;
import wykopapi.api.request.ApiRequest;

import java.util.Map;

import static org.junit.Assert.*;

public class LoginRequestTest {
    private Extractor extractor;
    private LoginRequest loginRequest;

    @Before
    public void setUp() throws Exception {
        extractor = new Extractor();
        loginRequest = new LoginRequest.Builder("accountkey").build();
    }

    @Test
    public void getRequest() throws Exception {
        Map<String, String> parameters = extractor.extract(loginRequest.getRequest().body());

        assertEquals("https://a.wykop.pl/user/login", loginRequest.getRequest().url().toString());
        assertEquals(1, parameters.size());
        assertEquals("accountkey", parameters.get("accountkey"));
    }

    @Test
    public void getReturnType() throws Exception {
        assertEquals(Profile.class, loginRequest.getReturnType());
    }

}