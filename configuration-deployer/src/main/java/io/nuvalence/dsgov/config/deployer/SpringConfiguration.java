package io.nuvalence.dsgov.config.deployer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.nuvalence.dsgov.config.deployer.auth.AuthTokenProvider;
import io.nuvalence.dsgov.config.deployer.client.CamundaClientFactory;
import io.nuvalence.dsgov.config.deployer.client.NotificationServiceAdminClientFactory;
import io.nuvalence.dsgov.config.deployer.client.WorkManagerAdminClientFactory;
import io.nuvalence.dsgov.config.deployer.util.JacocoIgnoreInGeneratedReport;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.io.InputStream;

/**
 * Provides specially configured beans to the Spring context.
 */
@Configuration
@ComponentScan("io.nuvalence.dsgov.config.deployer")
@JacocoIgnoreInGeneratedReport(
        reason = "Bean initialization may have side effects that make unit tests difficult.")
public class SpringConfiguration {
    /**
     * Jackson ObjectMapper configured to parse YAML.
     *
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper(new YAMLFactory()).findAndRegisterModules();
    }

    /**
     * Deployer application configuration.
     *
     * @param objectMapper Object mapper used to parse configuration file
     * @return DeployerConfiguration
     * @throws IOException If there is an IO error parsing configuration file
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DeployerConfiguration getDeployerConfiguration(final ObjectMapper objectMapper)
            throws IOException {
        try (InputStream inputStream =
                DeployerConfiguration.class.getResourceAsStream("/deployer-config.yaml")) {
            return objectMapper.readValue(inputStream, DeployerConfiguration.class);
        }
    }

    /**
     * Returns a configured AuthTokenProvider.
     *
     * @param configuration Deployer application configuration
     * @param context EnvironmentAwareContext to register AuthTokenProvider with
     * @return AuthTokenProvider
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AuthTokenProvider getAuthTokenProvider(
            final DeployerConfiguration configuration, final EnvironmentAwareContext context) {
        final AuthTokenProvider authTokenProvider = new AuthTokenProvider(configuration);
        context.registerComponent(authTokenProvider);

        return authTokenProvider;
    }

    /**
     * Returns a configured CamundaClientFactory.
     *
     * @param configuration Deployer application configuration
     * @param tokenProvider Token provider clients will use for authentication
     * @param context EnvironmentAwareContext to register CamundaClientFactory with
     * @return CamundaClientFactory
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CamundaClientFactory getCamundaClientFactory(
            final DeployerConfiguration configuration,
            final AuthTokenProvider tokenProvider,
            final EnvironmentAwareContext context) {
        final CamundaClientFactory factory = new CamundaClientFactory(configuration, tokenProvider);
        context.registerComponent(factory);

        return factory;
    }

    /**
     * Returns a configured WorkManagerAdminClientFactory.
     *
     * @param configuration Deployer application configuration
     * @param tokenProvider Token provider clients will use for authentication
     * @param context EnvironmentAwareContext to register WorkManagerAdminClientFactory with
     * @return WorkManagerAdminClientFactory
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public WorkManagerAdminClientFactory getWorkManagerAdminClientFactory(
            final DeployerConfiguration configuration,
            final AuthTokenProvider tokenProvider,
            final EnvironmentAwareContext context) {
        final WorkManagerAdminClientFactory factory =
                new WorkManagerAdminClientFactory(configuration, tokenProvider);
        context.registerComponent(factory);

        return factory;
    }

    /**
     * Returns a configured NotificationServiceAdminClientFactory.
     *
     * @param configuration Deployer application configuration
     * @param tokenProvider Token provider clients will use for authentication
     * @param context EnvironmentAwareContext to register NotificationServiceAdminClientFactory with
     * @return NotificationServiceAdminClientFactory
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public NotificationServiceAdminClientFactory getNotificationServiceAdminClientFactory(
            final DeployerConfiguration configuration,
            final AuthTokenProvider tokenProvider,
            final EnvironmentAwareContext context) {
        final NotificationServiceAdminClientFactory factory =
                new NotificationServiceAdminClientFactory(configuration, tokenProvider);
        context.registerComponent(factory);

        return factory;
    }
}
