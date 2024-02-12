package io.nuvalence.dsgov.config.deployer.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Optional;

class WorkflowRepositoryTest {
    private WorkflowRepository repository;

    @BeforeEach
    void setUp() {
        repository =
                new WorkflowRepository(
                        FileUtils.toFile(
                                getClass().getResource("/test-data/test-config-set/workflow")));
    }

    @Test
    void getAllWorkflows() {
        final List<File> allWorkflows = repository.getAllWorkflows();

        assertEquals(1, allWorkflows.size());
        assertTrue(
                allWorkflows.contains(
                        FileUtils.toFile(
                                getClass()
                                        .getResource(
                                                "/test-data/test-config-set/workflow/TestProcess.bpmn"))));
    }

    @Test
    void getAllDecisionTables() {
        final List<File> allDecisionTables = repository.getAllDecisionTables();

        assertEquals(1, allDecisionTables.size());
        assertTrue(
                allDecisionTables.contains(
                        FileUtils.toFile(
                                getClass()
                                        .getResource(
                                                "/test-data/test-config-set/workflow/TestDecision.dmn"))));
    }

    @Test
    void getSingleWorkflow() {
        final Optional<File> result = repository.getSingleWorkflow("TestProcess");

        assertTrue(result.isPresent());
        assertEquals(
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/workflow/TestProcess.bpmn")),
                result.get());
    }

    @Test
    void getSingleWorkflow_NotFound() {
        final Optional<File> result = repository.getSingleWorkflow("Missing");

        assertTrue(result.isEmpty());
    }

    @Test
    void getSingleDecisionTable() {
        final Optional<File> result = repository.getSingleDecisionTable("TestDecision");

        assertTrue(result.isPresent());
        assertEquals(
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/workflow/TestDecision.dmn")),
                result.get());
    }

    @Test
    void getSingleDecisionTable_NotFound() {
        final Optional<File> result = repository.getSingleDecisionTable("Missing");

        assertTrue(result.isEmpty());
    }
}
