package io.nuvalence.dsgov.config.deployer.client;

import io.nuvalence.dsgov.camunda.client.DeploymentAPI;
import io.nuvalence.dsgov.config.deployer.DeployerConfiguration;
import io.nuvalence.dsgov.config.deployer.EnvironmentAware;
import io.nuvalence.dsgov.config.deployer.EnvironmentConfiguration;
import io.nuvalence.dsgov.config.deployer.auth.AuthTokenProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.HttpPost;

import java.util.function.Consumer;

/**
 * Handles creation of Camunda client instances targeting the current environment.
 */
@RequiredArgsConstructor
public class CamundaClientFactory implements EnvironmentAware {
    private static final String BASE_PATH = "/engine-rest";
    private final DeployerConfiguration configuration;
    private final AuthTokenProvider tokenProvider;
    @Getter private String environment;

    @Override
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    /**
     * Creates a new Camunda client.
     *
     * @return Camunda Client (DeploymentApi)
     */
    public DeploymentAPI create() {
        if (environment == null) {
            throw new IllegalStateException(
                    "Cannot create Camunda client before environment has been set.");
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
        Consumer<HttpPost> requestInterceptor =
                (HttpPost httpPost) -> {
                    httpPost.setHeader("Authorization", "Bearer " + tokenProvider.getToken());
                };
        final DeploymentAPI deploymentApi =
                new DeploymentAPI(
                        environmentConfiguration.getWorkManagerBaseUrl() + BASE_PATH,
                        requestInterceptor);
        return deploymentApi;
    }
}
