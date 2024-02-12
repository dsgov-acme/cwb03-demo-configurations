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

class TransactionRepositoryTest {
    public static final String TRANSACTION_FILE =
            "/test-data/test-config-set/transaction/TestTransaction.yaml";
    public static final String DEFAULT_FORM_FILE =
            "/test-data/test-config-set/transaction/TestTransaction-form-Default.yaml";
    public static final String OTHER_FORM_FILE =
            "/test-data/test-config-set/transaction/TestTransaction-form-Other.yaml";
    private TransactionRepository repository;

    @BeforeEach
    void setUp() {
        repository =
                new TransactionRepository(
                        FileUtils.toFile(
                                getClass().getResource("/test-data/test-config-set/transaction")));
    }

    @Test
    void getAllTransactions() {
        final TransactionRepository.TransactionFileBundle expected =
                new TransactionRepository.TransactionFileBundle(
                        FileUtils.toFile(getClass().getResource(TRANSACTION_FILE)),
                        Set.of(
                                Objects.requireNonNull(
                                        FileUtils.toFile(
                                                getClass().getResource(DEFAULT_FORM_FILE))),
                                Objects.requireNonNull(
                                        FileUtils.toFile(
                                                getClass().getResource(OTHER_FORM_FILE)))));

        final List<TransactionRepository.TransactionFileBundle> allTransactions =
                repository.getAllTransactions();

        assertEquals(1, allTransactions.size());
        assertTrue(allTransactions.contains(expected));
    }

    @Test
    void getSingleTransaction() {
        final TransactionRepository.TransactionFileBundle expected =
                new TransactionRepository.TransactionFileBundle(
                        FileUtils.toFile(getClass().getResource(TRANSACTION_FILE)),
                        Set.of(
                                Objects.requireNonNull(
                                        FileUtils.toFile(
                                                getClass().getResource(DEFAULT_FORM_FILE))),
                                Objects.requireNonNull(
                                        FileUtils.toFile(
                                                getClass().getResource(OTHER_FORM_FILE)))));

        final Optional<TransactionRepository.TransactionFileBundle> result =
                repository.getSingleTransaction("TestTransaction");

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }

    @Test
    void getSingleTransaction_NotFound() {
        final Optional<TransactionRepository.TransactionFileBundle> result =
                repository.getSingleTransaction("Missing");

        assertTrue(result.isEmpty());
    }
}
