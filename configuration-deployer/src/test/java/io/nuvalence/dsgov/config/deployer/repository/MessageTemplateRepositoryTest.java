package io.nuvalence.dsgov.config.deployer.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class MessageTemplateRepositoryTest {
    private MessageTemplateRepository repository;

    @BeforeEach
    void setUp() {
        repository =
                new MessageTemplateRepository(
                        FileUtils.toFile(
                                getClass()
                                        .getResource(
                                                "/test-data/test-config-set/notification/message-template")));
    }

    @Test
    void getAllMessageTemplatesTest() {
        final List<File> allMessageTemplates = repository.getAllMessageTemplates();

        assertEquals(1, allMessageTemplates.size());
        assertTrue(
                allMessageTemplates.contains(
                        FileUtils.toFile(
                                getClass()
                                        .getResource(
                                                "/test-data/test-config-set/notification/message-template"
                                                        + "/TestMessageTemplate.yaml"))));
    }

    @Test
    void getSingleMessageTemplateTest() {
        final Optional<File> result = repository.getSingleMessageTemplate("TestMessageTemplate");

        assertTrue(result.isPresent());
        assertEquals(
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/notification/message-template"
                                                + "/TestMessageTemplate.yaml")),
                result.get());
    }

    @Test
    void getSingleMessageTemplate_NotFound() {
        final Optional<File> result = repository.getSingleMessageTemplate("Missing");

        assertTrue(result.isEmpty());
    }
}
