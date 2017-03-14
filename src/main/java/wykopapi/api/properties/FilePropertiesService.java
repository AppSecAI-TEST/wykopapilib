package wykopapi.api.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public final class FilePropertiesService implements PropertiesService {
    private final String appKey;
    private final String secret;
    private final String accountKey;

    public FilePropertiesService(String filename) throws IOException {
        Properties properties = new Properties();
        properties.load(new InputStreamReader(new FileInputStream(filename)));
        appKey = properties.getProperty("appkey", "");
        secret = properties.getProperty("secret", "");
        accountKey = properties.getProperty("accountkey", "");
    }

    @Override
    public String getAppKey() {
        return appKey;
    }

    @Override
    public String getSecret() {
        return secret;
    }

    @Override
    public String getAccountKey() {
        return accountKey;
    }
}
