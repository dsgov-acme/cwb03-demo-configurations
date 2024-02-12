package io.nuvalence.dsgov.config.deployer.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.nuvalence.dsgov.config.deployer.DeployerConfiguration;
import io.nuvalence.dsgov.config.deployer.EnvironmentConfiguration;
import io.nuvalence.dsgov.config.deployer.auth.AuthTokenProvider;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class WorkManagerAdminClientFactoryTest {

    @Test
    void create() {
        final AuthTokenProvider authTokenProvider = mock(AuthTokenProvider.class);
        final DeployerConfiguration config = mock(DeployerConfiguration.class);
        final WorkManagerAdminClientFactory factory =
                new WorkManagerAdminClientFactory(config, authTokenProvider);
        final EnvironmentConfiguration environmentConfig = new EnvironmentConfiguration();
        environmentConfig.setWorkManagerBaseUrl("http://localhost");
        when(config.getEnvironment("test")).thenReturn(Optional.of(environmentConfig));
        factory.setEnvironment("test");

        assertDoesNotThrow(factory::create);
    }
}
