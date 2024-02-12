package io.nuvalence.dsgov.config.deployer.repository;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.Optional;

/**
 * Repository for transaction definition set order.
 */
@RequiredArgsConstructor
public class TransactionDefinitionSetOrderRepository {
    private final File transactionDefinitionSetOrderDirectory;

    /**
     * Gets the transaction definition set order file.
     *
     * @return the transaction definition set order file
     */
    public Optional<File> getTransactionDefinitionSetOrder() {
        final File file = new File(transactionDefinitionSetOrderDirectory, "Dashboards.yaml");

        return file.exists() ? Optional.of(file) : Optional.empty();
    }
}
