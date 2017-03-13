package wykopapi.properties;

final class SimplePropertiesService implements PropertiesService {
    private static final String APP_KEY = "<appkey>";
    private static final String SECRET = "<secret>";
    private static final String ACCOUNT_KEY = "<accountkey>";

    @Override
    public String getAppKey() {
        return APP_KEY;
    }

    @Override
    public String getSecret() {
        return SECRET;
    }

    @Override
    public String getAccountKey() {
        return ACCOUNT_KEY;
    }
}
