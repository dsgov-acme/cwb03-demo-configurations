package io.nuvalence.dsgov.config.deployer.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Provides access to email layout data stored on local disk.
 */
@RequiredArgsConstructor
public class EmailLayoutRepository {
    @Getter private final File emailLayoutDirectory;

    /**
     * Get all email layout files present.
     *
     * @return List of Files found
     */
    public List<File> getAllEmailLayouts() {
        return DirectoryUtility.getMatchingFiles(emailLayoutDirectory, "*.yaml");
    }

    /**
     * Get a single email layout file for a given key if it exists.
     *
     * @param key Email Layout Key to search for
     * @return File if found
     */
    public Optional<File> getSingleEmailLayout(final String key) {
        final File file = new File(emailLayoutDirectory, key + ".yaml");

        return file.exists() ? Optional.of(file) : Optional.empty();
    }
}
