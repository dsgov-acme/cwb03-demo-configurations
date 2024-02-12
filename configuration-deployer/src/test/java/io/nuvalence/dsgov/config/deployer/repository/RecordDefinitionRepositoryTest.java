package io.nuvalence.dsgov.config.deployer.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

class RecordDefinitionRepositoryTest {
    public static final String RECORD_DEFINITION_FILE =
            "/test-data/test-config-set/record-definition/TestRecordDefinition.yaml";

    public static final String DEFAULT_FORM_FILE =
            "/test-data/test-config-set/record-definition/TestRecordDefinition-form-Default.yaml";
    public static final String OTHER_FORM_FILE =
            "/test-data/test-config-set/record-definition/TestRecordDefinition-form-Other.yaml";

    private RecordDefinitionRepository repository;

    @BeforeEach
    void setUp() {
        repository =
                new RecordDefinitionRepository(
                        FileUtils.toFile(
                                getClass()
                                        .getResource(
                                                "/test-data/test-config-set/record-definition")));
    }

    @Test
    void getAllRecordDefinitions() {
        final RecordDefinitionRepository.RecordDefinitionFileBundle expected =
                new RecordDefinitionRepository.RecordDefinitionFileBundle(
                        FileUtils.toFile(getClass().getResource(RECORD_DEFINITION_FILE)),
                        Set.of(
                                Objects.requireNonNull(
                                        FileUtils.toFile(
                                                getClass().getResource(DEFAULT_FORM_FILE))),
                                Objects.requireNonNull(
                                        FileUtils.toFile(
                                                getClass().getResource(OTHER_FORM_FILE)))));

        final List<RecordDefinitionRepository.RecordDefinitionFileBundle> allRecordDefinitions =
                repository.getAllRecordDefinitions();

        assertEquals(1, allRecordDefinitions.size());
        assertTrue(allRecordDefinitions.contains(expected));
    }

    @Test
    void getSingleRecordDefinition() {
        final RecordDefinitionRepository.RecordDefinitionFileBundle expected =
                new RecordDefinitionRepository.RecordDefinitionFileBundle(
                        FileUtils.toFile(getClass().getResource(RECORD_DEFINITION_FILE)),
                        Set.of(
                                Objects.requireNonNull(
                                        FileUtils.toFile(
                                                getClass().getResource(DEFAULT_FORM_FILE))),
                                Objects.requireNonNull(
                                        FileUtils.toFile(
                                                getClass().getResource(OTHER_FORM_FILE)))));

        final Optional<RecordDefinitionRepository.RecordDefinitionFileBundle> result =
                repository.getSingleRecordDefinition("TestRecordDefinition");

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }

    @Test
    void getSingleRecordDefinition_NotFound() {
        final Optional<RecordDefinitionRepository.RecordDefinitionFileBundle> result =
                repository.getSingleRecordDefinition("Missing");

        assertTrue(result.isEmpty());
    }
}
