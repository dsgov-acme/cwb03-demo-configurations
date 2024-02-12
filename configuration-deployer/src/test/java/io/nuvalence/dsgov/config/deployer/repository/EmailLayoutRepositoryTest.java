package io.nuvalence.dsgov.config.deployer.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class EmailLayoutRepositoryTest {

    private EmailLayoutRepository repository;

    @BeforeEach
    void setUp() {
        repository =
                new EmailLayoutRepository(
                        FileUtils.toFile(
                                getClass()
                                        .getResource(
                                                "/test-data/test-config-set/notification/email-layout")));
    }

    @Test
    void getAllEmailLayoutsTest() {
        final List<File> allEmailLayouts = repository.getAllEmailLayouts();

        assertEquals(1, allEmailLayouts.size());
        assertTrue(
                allEmailLayouts.contains(
                        FileUtils.toFile(
                                getClass()
                                        .getResource(
                                                "/test-data/test-config-set/notification/email-layout"
                                                        + "/TestEmailLayout.yaml"))));
    }

    @Test
    void getSingleEmailLayoutTest() {
        final Optional<File> result = repository.getSingleEmailLayout("TestEmailLayout");

        assertTrue(result.isPresent());
        assertEquals(
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/notification/email-layout"
                                                + "/TestEmailLayout.yaml")),
                result.get());
    }

    @Test
    void getSingleEmailLayout_NotFound() {
        final Optional<File> result = repository.getSingleEmailLayout("Missing");

        assertTrue(result.isEmpty());
    }
}
