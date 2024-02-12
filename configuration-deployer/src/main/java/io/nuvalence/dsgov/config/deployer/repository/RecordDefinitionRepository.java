package io.nuvalence.dsgov.config.deployer.repository;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Provides access to record definition configuration data stored on local disk.
 */
@RequiredArgsConstructor
public class RecordDefinitionRepository {
    @Getter private final File recordDefinitionDirectory;

    /**
     * Get all record definition configuration file bundles present.
     *
     * @return List of RecordDefinitionFileBundles found
     */
    public List<RecordDefinitionFileBundle> getAllRecordDefinitions() {
        return getAllRootRecordDefinitionFiles().stream()
                .map(this::bundle)
                .collect(Collectors.toList());
    }

    /**
     * Get a single record definition configuration file bundle for a given key if it exists.
     *
     * @param key Record Definition Key to search for
     * @return RecordDefinitionFileBundle if found
     */
    public Optional<RecordDefinitionFileBundle> getSingleRecordDefinition(String key) {
        final File file = new File(recordDefinitionDirectory, key + ".yaml");

        return file.exists() ? Optional.of(bundle(file)) : Optional.empty();
    }

    private List<File> getAllRootRecordDefinitionFiles() {
        return DirectoryUtility.getMatchingFiles(recordDefinitionDirectory, "*.yaml").stream()
                .filter(file -> !file.getName().contains("-form-"))
                .collect(Collectors.toList());
    }

    private RecordDefinitionFileBundle bundle(final File recordDefinitionFile) {
        return new RecordDefinitionFileBundle(
                recordDefinitionFile,
                getFormFilesForRecordDefinition(
                        FilenameUtils.removeExtension(recordDefinitionFile.getName())));
    }

    private Set<File> getFormFilesForRecordDefinition(final String recordDefinitionKey) {
        return new HashSet<>(
                DirectoryUtility.getMatchingFiles(
                        recordDefinitionDirectory, recordDefinitionKey + "-form-*.yaml"));
    }

    /**
     * Bundle of files that combine to configure a single Record Definition.
     */
    @RequiredArgsConstructor
    @Getter
    @EqualsAndHashCode
    @ToString
    public static class RecordDefinitionFileBundle {
        private final File recordDefinitionFile;
        private final Set<File> formFiles;
    }
}
