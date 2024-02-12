package io.nuvalence.dsgov.config.deployer.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Optional;

public class TransactionDefinitionSetOrderRepositoryTest {
    private TransactionDefinitionSetOrderRepository repository;

    @BeforeEach
    void setUp() {
        repository =
                new TransactionDefinitionSetOrderRepository(
                        FileUtils.toFile(getClass().getResource("/test-data/test-config-set")));
    }

    @Test
    void testGetTransactionDefinitionSetOrder() {
        final Optional<File> result = repository.getTransactionDefinitionSetOrder();

        assertTrue(result.isPresent());
        assertEquals(
                FileUtils.toFile(
                        getClass().getResource("/test-data/test-config-set/Dashboards.yaml")),
                result.get());
    }
}
