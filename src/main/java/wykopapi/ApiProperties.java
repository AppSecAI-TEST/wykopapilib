package wykopapi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public final class ApiProperties {
    private final String appKey;
    private final String secret;
    private final String accountKey;

    public ApiProperties() {
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream("src/main/resources/application.properties")));
            appKey = properties.getProperty("appkey");
            secret = properties.getProperty("secret");
            accountKey = properties.getProperty("accountkey");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public String getAppKey() {
        return appKey;
    }

    public String getSecret() {
        return secret;
    }

    public String getAccountKey() {
        return accountKey;
    }
}
