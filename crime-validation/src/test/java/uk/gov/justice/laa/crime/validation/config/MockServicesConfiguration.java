package uk.gov.justice.laa.crime.validation.config;

public class MockServicesConfiguration {

    public static ServicesConfiguration getConfiguration(int port) {

        String host = String.format("http://localhost:%s", port);
        ServicesConfiguration servicesConfiguration = new ServicesConfiguration();

        ServicesConfiguration.MaatApi maatApiConfiguration = new ServicesConfiguration.MaatApi();

        ServicesConfiguration.MaatApi.UserEndpoints userEndpoints =
                new ServicesConfiguration.MaatApi.UserEndpoints(
                        "/api/internal/v1/users"
                );

        maatApiConfiguration.setBaseUrl(host);
        maatApiConfiguration.setUserEndpoints(userEndpoints);

        servicesConfiguration.setOAuthEnabled(false);
        servicesConfiguration.setMaatApi(maatApiConfiguration);

        return servicesConfiguration;
    }
}
