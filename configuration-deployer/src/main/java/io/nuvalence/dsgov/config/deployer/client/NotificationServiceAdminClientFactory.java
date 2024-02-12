package io.nuvalence.dsgov.config.deployer.client;

import io.nuvalence.config.notification.client.ApiClient;
import io.nuvalence.config.notification.client.generated.api.AdminNotificationApi;
import io.nuvalence.dsgov.config.deployer.DeployerConfiguration;
import io.nuvalence.dsgov.config.deployer.EnvironmentAware;
import io.nuvalence.dsgov.config.deployer.EnvironmentConfiguration;
import io.nuvalence.dsgov.config.deployer.auth.AuthTokenProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Handles creation of notification service admin client instances targeting the current environment.
 */
@RequiredArgsConstructor
public class NotificationServiceAdminClientFactory implements EnvironmentAware {
    private final DeployerConfiguration configuration;
    private final AuthTokenProvider tokenProvider;

    @Getter private String environment;

    @Override
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    /**
     * Creates a new notification-service admin client.
     *
     * @return Notification Client (AdminApi)
     */
    public AdminNotificationApi create() {
        if (environment == null) {
            throw new IllegalStateException(
                    "Cannot create Notification Service client before environment has been set.");
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
        apiClient.updateBaseUri(environmentConfiguration.getNotificationServiceBaseUrl());
        apiClient.setRequestInterceptor(
                request -> request.header("authorization", "Bearer " + tokenProvider.getToken()));

        return new AdminNotificationApi(apiClient);
    }
}
