package io.nuvalence.dsgov.config.deployer.repository;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Repository for transaction definition sets.
 */
@RequiredArgsConstructor
public class TransactionDefinitionSetRepository {
    private final File transactionDefinitionSetDirectory;

    public List<File> getAllTransactionDefinitionSets() {
        return DirectoryUtility.getMatchingFiles(transactionDefinitionSetDirectory, "*.yaml");
    }

    /**
     * Gets a single transaction definition set.
     *
     * @param key the key of the transaction definition set file
     * @return the transaction definition set
     */
    public Optional<File> getSingleTransactionDefinition(final String key) {
        final File file = new File(transactionDefinitionSetDirectory, key + ".yaml");

        return file.exists() ? Optional.of(file) : Optional.empty();
    }
}
