package io.nuvalence.dsgov.config.deployer.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Optional;

class SchemaRepositoryTest {
    private SchemaRepository repository;

    @BeforeEach
    void setUp() {
        repository =
                new SchemaRepository(
                        FileUtils.toFile(
                                getClass().getResource("/test-data/test-config-set/schema")));
    }

    @Test
    void getAllSchemas() {
        final List<File> allSchemas = repository.getAllSchemas();

        assertEquals(1, allSchemas.size());
        assertTrue(
                allSchemas.contains(
                        FileUtils.toFile(
                                getClass()
                                        .getResource(
                                                "/test-data/test-config-set/schema/SimpleSchema.yaml"))));
    }

    @Test
    void getSingleSchema() {
        final Optional<File> result = repository.getSingleSchema("SimpleSchema");

        assertTrue(result.isPresent());
        assertEquals(
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/schema/SimpleSchema.yaml")),
                result.get());
    }

    @Test
    void getSingleSchema_NotFound() {
        final Optional<File> result = repository.getSingleSchema("Missing");

        assertTrue(result.isEmpty());
    }
}
