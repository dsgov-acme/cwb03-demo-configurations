package io.nuvalence.dsgov.config.deployer.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import io.nuvalence.dsgov.config.deployer.DeployerConfiguration;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ConfigurationRepositoryTest {

    @Test
    void correctlyConfiguresRepositories() {
        final DeployerConfiguration config = mock(DeployerConfiguration.class);
        Mockito.when(config.getConfigurationDirectory())
                .thenReturn(FileUtils.toFile(getClass().getResource("/test-data")));

        final ConfigurationRepository repository =
                new ConfigurationRepository(config, "test-config-set");

        assertEquals(
                FileUtils.toFile(getClass().getResource("/test-data/test-config-set/schema")),
                repository.getSchemaRepository().getSchemaDirectory());
        assertEquals(
                FileUtils.toFile(getClass().getResource("/test-data/test-config-set/transaction")),
                repository.getTransactionRepository().getTransactionDirectory());
        assertEquals(
                FileUtils.toFile(getClass().getResource("/test-data/test-config-set/workflow")),
                repository.getWorkflowRepository().getWorkflowDirectory());
    }
}
