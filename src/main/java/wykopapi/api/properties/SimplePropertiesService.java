package wykopapi.api.properties;

public final class SimplePropertiesService implements PropertiesService {
    private final String appKey;
    private final String secret;
    private final String accountKey;

    private SimplePropertiesService(String appKey, String secret, String accountKey) {
        this.appKey = appKey;
        this.secret = secret;
        this.accountKey = accountKey;
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

    public static class Builder {
        private String appKey;
        private String secret;
        private String accountKey;

        public Builder(String appKey, String secret) {
            if (appKey == null || secret == null) throw new NullPointerException();
            this.appKey = appKey;
            this.secret = secret;
        }

        public Builder setAccountKey(String accountKey) {
            this.accountKey = accountKey;
            return this;
        }

        public SimplePropertiesService build() {
            return new SimplePropertiesService(appKey, secret, accountKey);
        }
    }
}
