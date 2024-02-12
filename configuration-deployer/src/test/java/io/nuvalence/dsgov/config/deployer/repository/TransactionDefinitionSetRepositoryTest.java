package io.nuvalence.dsgov.config.deployer.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class TransactionDefinitionSetRepositoryTest {
    private TransactionDefinitionSetRepository repository;

    @BeforeEach
    void setUp() {
        repository =
                new TransactionDefinitionSetRepository(
                        FileUtils.toFile(
                                getClass()
                                        .getResource(
                                                "/test-data/test-config-set/transaction-set")));
    }

    @Test
    void getAllTransactionDefinitionSets() {
        final List<File> allTransactionDefinitionSets =
                repository.getAllTransactionDefinitionSets();

        assertEquals(1, allTransactionDefinitionSets.size());
        assertTrue(
                allTransactionDefinitionSets.contains(
                        FileUtils.toFile(
                                getClass()
                                        .getResource(
                                                "/test-data/test-config-set/transaction-set/SimpleSet.yaml"))));
    }

    @Test
    void getSingleSchema() {
        final Optional<File> result = repository.getSingleTransactionDefinition("SimpleSet");

        assertTrue(result.isPresent());
        assertEquals(
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/transaction-set/SimpleSet.yaml")),
                result.get());
    }

    @Test
    void getSingleSchema_NotFound() {
        final Optional<File> result = repository.getSingleTransactionDefinition("Missing");

        assertTrue(result.isEmpty());
    }
}
