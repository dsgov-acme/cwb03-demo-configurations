package io.nuvalence.dsgov.config.deployer.client;

import io.nuvalence.config.workmanager.client.ApiClient;
import io.nuvalence.config.workmanager.client.generated.api.AdminApi;
import io.nuvalence.dsgov.config.deployer.DeployerConfiguration;
import io.nuvalence.dsgov.config.deployer.EnvironmentAware;
import io.nuvalence.dsgov.config.deployer.EnvironmentConfiguration;
import io.nuvalence.dsgov.config.deployer.auth.AuthTokenProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Handles creation of work-manager admin client instances targeting the current environment.
 */
@RequiredArgsConstructor
public class WorkManagerAdminClientFactory implements EnvironmentAware {
    private final DeployerConfiguration configuration;
    private final AuthTokenProvider tokenProvider;
    @Getter private String environment;

    @Override
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    /**
     * Creates a new work-manager admin client.
     *
     * @return Camunda Client (AdminApi)
     */
    public AdminApi create() {
        if (environment == null) {
            throw new IllegalStateException(
                    "Cannot create Work Manager client before environment has been set.");
        }
        final EnvironmentConfiguration environmentConfiguration =
                configuration
                        .getEnvironment(environment)
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "Environment ["
                                                        + environment
                                                        + "] does not exist."));
        final ApiClient apiClient = new ApiClient();
        apiClient.updateBaseUri(environmentConfiguration.getWorkManagerBaseUrl() + "/api/v1");
        apiClient.setRequestInterceptor(
                request -> request.header("authorization", "Bearer " + tokenProvider.getToken()));

        return new AdminApi(apiClient);
    }
}
