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
 * Provides access to transaction configuration data stored on local disk.
 */
@RequiredArgsConstructor
public class TransactionRepository {
    @Getter private final File transactionDirectory;

    /**
     * Get all transaction configuration file bundles present.
     *
     * @return List of TransactionFileBundles found
     */
    public List<TransactionFileBundle> getAllTransactions() {
        return getAllRootTransactionFiles().stream().map(this::bundle).collect(Collectors.toList());
    }

    /**
     * Get a single transaction configuration file bundle for a given key if it exists.
     *
     * @param key Transaction Definition Key to search for
     * @return TransactionFileBundle if found
     */
    public Optional<TransactionFileBundle> getSingleTransaction(String key) {
        final File file = new File(transactionDirectory, key + ".yaml");

        return file.exists() ? Optional.of(bundle(file)) : Optional.empty();
    }

    private List<File> getAllRootTransactionFiles() {
        return DirectoryUtility.getMatchingFiles(transactionDirectory, "*.yaml").stream()
                .filter(file -> !file.getName().contains("-form-"))
                .collect(Collectors.toList());
    }

    private TransactionFileBundle bundle(final File transactionFile) {
        return new TransactionFileBundle(
                transactionFile,
                getFormFilesForTransaction(
                        FilenameUtils.removeExtension(transactionFile.getName())));
    }

    private Set<File> getFormFilesForTransaction(final String transactionKey) {
        return new HashSet<>(
                DirectoryUtility.getMatchingFiles(
                        transactionDirectory, transactionKey + "-form-*.yaml"));
    }

    /**
     * Bundle of files that combine to configure a single Transaction Definition.
     */
    @RequiredArgsConstructor
    @Getter
    @EqualsAndHashCode
    @ToString
    public static class TransactionFileBundle {
        private final File transactionFile;
        private final Set<File> formFiles;
    }
}
