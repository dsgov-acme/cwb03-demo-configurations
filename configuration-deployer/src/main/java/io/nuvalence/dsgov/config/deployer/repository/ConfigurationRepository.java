package io.nuvalence.dsgov.config.deployer.repository;

import io.nuvalence.dsgov.config.deployer.DeployerConfiguration;
import lombok.Getter;

import java.io.File;

/**
 * Provides access to configuration data stored on local disk.
 */
@SuppressWarnings("ClassDataAbstractionCoupling")
public class ConfigurationRepository {
    private static final String SCHEMA_DIR = "schema";
    private static final String WORKFLOW_DIR = "workflow";
    private static final String TRANSACTION_DIR = "transaction";
    private static final String EMAIL_LAYOUT_DIR = "notification/email-layout";
    private static final String MESSAGE_TEMPLATE_DIR = "notification/message-template";
    private static final String TRANSACTION_DEFINITION_SET_DIR = "transaction-set";
    private static final String TRANSACTION_DEFINITION_SET_ORDER_DIR = "";
    private static final String RECORD_DEFINITION_DIR = "record-definition";

    @Getter private final WorkflowRepository workflowRepository;
    @Getter private final SchemaRepository schemaRepository;
    @Getter private final TransactionRepository transactionRepository;
    @Getter private final EmailLayoutRepository emailLayoutRepository;
    @Getter private final MessageTemplateRepository messageTemplateRepository;
    @Getter private final TransactionDefinitionSetRepository transactionDefinitionSetRepository;

    @Getter
    private final TransactionDefinitionSetOrderRepository transactionDefinitionSetOrderRepository;

    @Getter private final RecordDefinitionRepository recordDefinitionRepository;

    /**
     * Constructs a new instance of ConfigurationRepository for a specific configuration set.
     *
     * @param configuration Deployer configuration
     * @param configSet ConfigSet to serve configuration for
     */
    public ConfigurationRepository(
            final DeployerConfiguration configuration, final String configSet) {
        final File repositoryRoot = new File(configuration.getConfigurationDirectory(), configSet);
        workflowRepository = new WorkflowRepository(new File(repositoryRoot, WORKFLOW_DIR));
        schemaRepository = new SchemaRepository(new File(repositoryRoot, SCHEMA_DIR));
        transactionRepository =
                new TransactionRepository(new File(repositoryRoot, TRANSACTION_DIR));
        emailLayoutRepository =
                new EmailLayoutRepository(new File(repositoryRoot, EMAIL_LAYOUT_DIR));
        messageTemplateRepository =
                new MessageTemplateRepository(new File(repositoryRoot, MESSAGE_TEMPLATE_DIR));
        transactionDefinitionSetRepository =
                new TransactionDefinitionSetRepository(
                        new File(repositoryRoot, TRANSACTION_DEFINITION_SET_DIR));
        transactionDefinitionSetOrderRepository =
                new TransactionDefinitionSetOrderRepository(
                        new File(repositoryRoot, TRANSACTION_DEFINITION_SET_ORDER_DIR));
        recordDefinitionRepository =
                new RecordDefinitionRepository(new File(repositoryRoot, RECORD_DEFINITION_DIR));
    }
}
