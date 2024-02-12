package io.nuvalence.dsgov.config.deployer.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Provides access to message template data stored on local disk.
 */
@RequiredArgsConstructor
public class MessageTemplateRepository {
    @Getter private final File messageTemplateDirectory;

    /**
     * Get all message template files present.
     *
     * @return List of Files found
     */
    public List<File> getAllMessageTemplates() {
        return DirectoryUtility.getMatchingFiles(messageTemplateDirectory, "*.yaml");
    }

    /**
     * Get a single message template file for a given key if it exists.
     *
     * @param key Message template Key to search for
     * @return File if found
     */
    public Optional<File> getSingleMessageTemplate(final String key) {
        final File file = new File(messageTemplateDirectory, key + ".yaml");

        return file.exists() ? Optional.of(file) : Optional.empty();
    }
}
