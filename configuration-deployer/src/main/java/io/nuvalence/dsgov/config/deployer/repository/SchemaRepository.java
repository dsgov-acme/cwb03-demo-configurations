package io.nuvalence.dsgov.config.deployer.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Provides access to schema configuration data stored on local disk.
 */
@RequiredArgsConstructor
public class SchemaRepository {
    @Getter private final File schemaDirectory;

    /**
     * Get all schema configuration files present.
     *
     * @return List of Files found
     */
    public List<File> getAllSchemas() {
        return DirectoryUtility.getMatchingFiles(schemaDirectory, "*.yaml");
    }

    /**
     * Get a single schema configuration file for a given key if it exists.
     *
     * @param key Schema Key to search for
     * @return File if found
     */
    public Optional<File> getSingleSchema(final String key) {
        final File file = new File(schemaDirectory, key + ".yaml");

        return file.exists() ? Optional.of(file) : Optional.empty();
    }
}
