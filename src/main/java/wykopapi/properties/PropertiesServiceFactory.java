package wykopapi.properties;

public final class PropertiesServiceFactory {
    private static final PropertiesService propertiesService = new FilePropertiesService();

    private PropertiesServiceFactory() {}

    public static PropertiesService getPropertiesService() {
        return propertiesService;
    }
}
