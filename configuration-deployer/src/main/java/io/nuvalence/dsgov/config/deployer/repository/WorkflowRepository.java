package io.nuvalence.dsgov.config.deployer.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Provides access to workflow configuration data stored on local disk.
 */
@RequiredArgsConstructor
public class WorkflowRepository {
    @Getter private final File workflowDirectory;

    /**
     * Get all workflow configuration files present.
     *
     * @return List of Files found
     */
    public List<File> getAllWorkflows() {
        return DirectoryUtility.getMatchingFiles(workflowDirectory, "*.bpmn");
    }

    /**
     * Get all decision table configuration files present.
     *
     * @return List of Files found
     */
    public List<File> getAllDecisionTables() {
        return DirectoryUtility.getMatchingFiles(workflowDirectory, "*.dmn");
    }

    /**
     * Get a single workflow configuration file for a given key if it exists.
     *
     * @param key Process Definition Key to search for
     * @return File if found
     */
    public Optional<File> getSingleWorkflow(final String key) {
        final File file = new File(workflowDirectory, key + ".bpmn");

        return file.exists() ? Optional.of(file) : Optional.empty();
    }

    /**
     * Get a single decision table configuration file for a given key if it exists.
     *
     * @param key Decision Definition Key to search for
     * @return File if found
     */
    public Optional<File> getSingleDecisionTable(final String key) {
        final File file = new File(workflowDirectory, key + ".dmn");

        return file.exists() ? Optional.of(file) : Optional.empty();
    }
}
