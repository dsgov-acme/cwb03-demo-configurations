package io.nuvalence.dsgov.config.deployer;

import io.nuvalence.dsgov.config.deployer.repository.ConfigurationRepository;
import io.nuvalence.dsgov.config.deployer.repository.RecordDefinitionRepository;
import io.nuvalence.dsgov.config.deployer.repository.TransactionRepository;
import io.nuvalence.dsgov.config.deployer.service.EmailLayoutService;
import io.nuvalence.dsgov.config.deployer.service.MessageTemplateService;
import io.nuvalence.dsgov.config.deployer.service.RecordDefinitionDeploymentService;
import io.nuvalence.dsgov.config.deployer.service.SchemaDeploymentService;
import io.nuvalence.dsgov.config.deployer.service.TransactionDefinitionSetDeploymentService;
import io.nuvalence.dsgov.config.deployer.service.TransactionDeploymentService;
import io.nuvalence.dsgov.config.deployer.service.WorkflowDeploymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Orchestrates configuration deployment based on selected criteria.
 */
@RequiredArgsConstructor
@Component
@Slf4j
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Deployer {
    private final DeployerConfiguration configuration;
    private final EnvironmentAwareContext environmentAwareContext;
    private final WorkflowDeploymentService workflowDeploymentService;
    private final SchemaDeploymentService schemaDeploymentService;
    private final TransactionDeploymentService transactionDeploymentService;
    private final EmailLayoutService emailLayoutService;
    private final MessageTemplateService messageTemplateService;
    private final TransactionDefinitionSetDeploymentService
            transactionDefinitionSetDeploymentService;
    private final RecordDefinitionDeploymentService recordDefinitionDeploymentService;

    private ConfigurationRepository repository;

    /**
     * Deploys all configuration.
     *
     * @param configSet   Top level set of configuration.
     * @param environment Target environment to deploy to
     */
    public void deployAll(final String configSet, final String environment) {
        init(configSet, environment);
        deployAllDecisionTables();
        deployAllWorkflows();
        deployAllSchemas();
        deployAllTransactionDefinitionSets();
        deployTransactionDefinitionSetOrder();
        deployAllTransactions();
        deployAllEmailLayouts();
        deployAllMessageTemplates();
        deployAllRecordDefinitions();
    }

    /**
     * Deploys a single configuration type.
     *
     * @param configSet   Top level set of configuration.
     * @param environment Target environment to deploy to
     * @param type        type of configuration to deploy
     */
    public void deployType(final String configSet, final String environment, final String type) {
        init(configSet, environment);
        if ("workflow".equals(type)) {
            deployAllDecisionTables();
            deployAllWorkflows();
        } else if ("schema".equals(type)) {
            deployAllSchemas();
        } else if ("transaction".equals(type)) {
            deployAllTransactions();
        } else if ("email-layout".equals(type)) {
            deployAllEmailLayouts();
        } else if ("message-template".equals(type)) {
            deployAllMessageTemplates();
        } else if ("transaction-definition-set".equals(type)) {
            deployAllTransactionDefinitionSets();
        } else if ("transaction-definition-set-order".equals(type)) {
            deployTransactionDefinitionSetOrder();
        } else if ("record-definition".equals(type)) {
            deployAllRecordDefinitions();
        }
    }

    /**
     * Deploys a single configuration by key if it exists in the configuration store.
     *
     * @param configSet   Top level set of configuration.
     * @param environment Target environment to deploy to
     * @param type        type of configuration to deploy
     * @param key         key of targeted configuration
     */
    public void deploySingle(
            final String configSet, final String environment, final String type, final String key) {
        init(configSet, environment);
        if ("workflow".equals(type)) {
            deploySingleWorkflow(key);
        } else if ("schema".equals(type)) {
            deploySingleSchema(key);
        } else if ("transaction".equals(type)) {
            deploySingleTransaction(key);
        } else if ("email-layout".equals(type)) {
            deploySingleEmailLayout(key);
        } else if ("message-template".equals(type)) {
            deploySingleMessageTemplate(key);
        } else if ("transaction-definition-set".equals(type)) {
            deploySingleTransactionDefinitionSet(key);
        } else if ("record-definition".equals(type)) {
            deploySingleRecordDefinition(key);
        }
    }

    private void deploySingleTransactionDefinitionSet(final String key) {
        try {
            final Optional<File> transactionDefinitionSetFile =
                    repository
                            .getTransactionDefinitionSetRepository()
                            .getSingleTransactionDefinition(key);
            if (transactionDefinitionSetFile.isPresent()) {
                transactionDefinitionSetDeploymentService.deploySingleTransactionDefinitionSet(
                        transactionDefinitionSetFile.get());
            } else {
                log.warn("No message template with key [{}] found.", key);
            }
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }

    private void deploySingleMessageTemplate(final String key) {
        try {
            final Optional<File> messageTemplateFile =
                    repository.getMessageTemplateRepository().getSingleMessageTemplate(key);
            if (messageTemplateFile.isPresent()) {
                messageTemplateService.deploySingleMessageTemplate(messageTemplateFile.get());
            } else {
                log.warn("No message template with key [{}] found.", key);
            }
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }

    private void deploySingleEmailLayout(final String key) {
        try {
            final Optional<File> emailLayoutFile =
                    repository.getEmailLayoutRepository().getSingleEmailLayout(key);
            if (emailLayoutFile.isPresent()) {
                emailLayoutService.deploySingleEmailLayout(emailLayoutFile.get());
            } else {
                log.warn("No email layout with key [{}] found.", key);
            }
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }

    private void deploySingleWorkflow(final String key) {
        try {
            final Optional<File> dmnFile =
                    repository.getWorkflowRepository().getSingleDecisionTable(key);
            final Optional<File> bpmnFile =
                    repository.getWorkflowRepository().getSingleWorkflow(key);
            if (dmnFile.isEmpty() && bpmnFile.isEmpty()) {
                log.warn("No workflow or decision table with key [{}] found.", key);
            }

            dmnFile.ifPresent(workflowDeploymentService::deploySingleDecisionTableFile);
            bpmnFile.ifPresent(workflowDeploymentService::deploySingleWorkflowFile);
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }

    private void deploySingleSchema(final String key) {
        try {
            final Optional<File> schemaFile = repository.getSchemaRepository().getSingleSchema(key);
            if (schemaFile.isPresent()) {
                schemaDeploymentService.deploySingleSchemaFile(schemaFile.get());
            } else {
                log.warn("No schema with key [{}] found.", key);
            }
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }

    private void deploySingleTransaction(final String key) {
        final Optional<TransactionRepository.TransactionFileBundle> bundle =
                repository.getTransactionRepository().getSingleTransaction(key);
        if (bundle.isPresent()) {
            deploySingleTransactionBundle(bundle.get());
        } else {
            log.warn("No transaction with key [{}] found.", key);
        }
    }

    private void deploySingleRecordDefinition(final String key) {
        final Optional<RecordDefinitionRepository.RecordDefinitionFileBundle> bundle =
                repository.getRecordDefinitionRepository().getSingleRecordDefinition(key);
        if (bundle.isPresent()) {
            deploySingleRecordDefinitionBundle(bundle.get());
        } else {
            log.warn("No record definition with key [{}] found.", key);
        }
    }

    private void init(final String configSet, final String environment) {
        repository = new ConfigurationRepository(configuration, configSet);
        environmentAwareContext.setEnvironment(environment);
    }

    private void deployAllWorkflows() {
        try {
            final List<File> workflowFiles = repository.getWorkflowRepository().getAllWorkflows();
            log.info("Found {} workflow files to deploy.", workflowFiles.size());
            workflowFiles.forEach(workflowDeploymentService::deploySingleWorkflowFile);
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }

    private void deployAllDecisionTables() {
        try {
            final List<File> decisionTableFiles =
                    repository.getWorkflowRepository().getAllDecisionTables();
            log.info("Found {} decision table files to deploy.", decisionTableFiles.size());
            decisionTableFiles.forEach(workflowDeploymentService::deploySingleDecisionTableFile);
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }

    private void deployAllSchemas() {
        try {
            final List<File> schemaFiles =
                    repository.getSchemaRepository().getAllSchemas().stream()
                            .sorted(Comparator.comparing(File::getName))
                            .collect(Collectors.toList());
            log.info("Found {} schema files to deploy.", schemaFiles.size());
            for (File schemaFile : schemaFiles) {
                schemaDeploymentService.deploySingleSchemaFile(schemaFile);
            }
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }

    private void deployAllEmailLayouts() {
        try {
            final List<File> emailLayoutFiles =
                    repository.getEmailLayoutRepository().getAllEmailLayouts();
            log.info("Found {} email layout files to deploy.", emailLayoutFiles.size());
            for (File emailLayoutFile : emailLayoutFiles) {
                emailLayoutService.deploySingleEmailLayout(emailLayoutFile);
            }
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }

    private void deployAllMessageTemplates() {
        try {
            final List<File> messageTemplateFiles =
                    repository.getMessageTemplateRepository().getAllMessageTemplates();
            log.info("Found {} message template files to deploy.", messageTemplateFiles.size());
            for (File messageTemplateFile : messageTemplateFiles) {
                messageTemplateService.deploySingleMessageTemplate(messageTemplateFile);
            }
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }

    private void deployAllTransactionDefinitionSets() {
        try {
            final List<File> transactionDefinitionSetFiles =
                    repository
                            .getTransactionDefinitionSetRepository()
                            .getAllTransactionDefinitionSets();
            log.info(
                    "Found {} transaction definition set files to deploy.",
                    transactionDefinitionSetFiles.size());
            for (File transactionDefinitionSetFile : transactionDefinitionSetFiles) {
                transactionDefinitionSetDeploymentService.deploySingleTransactionDefinitionSet(
                        transactionDefinitionSetFile);
            }
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }

    private void deployTransactionDefinitionSetOrder() {
        try {
            final Optional<File> transactionDefinitionSetOrderFile =
                    repository
                            .getTransactionDefinitionSetOrderRepository()
                            .getTransactionDefinitionSetOrder();
            if (transactionDefinitionSetOrderFile.isPresent()) {
                log.info("Found transaction definition set order file to deploy.");
                transactionDefinitionSetDeploymentService.deployTransactionDefinitionSetOrder(
                        transactionDefinitionSetOrderFile.get());
            }
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }

    private void deployAllTransactions() {
        final List<TransactionRepository.TransactionFileBundle> transactions =
                repository.getTransactionRepository().getAllTransactions();
        log.info("Found {} transaction files to deploy.", transactions.size());
        transactions.forEach(this::deploySingleTransactionBundle);
    }

    private void deploySingleTransactionBundle(
            final TransactionRepository.TransactionFileBundle bundle) {
        try {
            transactionDeploymentService.deploySingleTransactionFile(bundle.getTransactionFile());
            for (File file : bundle.getFormFiles()) {
                transactionDeploymentService.deploySingleTransactionFormFile(file);
            }
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }

    private void deployAllRecordDefinitions() {
        final List<RecordDefinitionRepository.RecordDefinitionFileBundle> recordDefinitions =
                repository.getRecordDefinitionRepository().getAllRecordDefinitions();
        log.info("Found {} record definition files to deploy.", recordDefinitions.size());
        recordDefinitions.forEach(this::deploySingleRecordDefinitionBundle);
    }

    private void deploySingleRecordDefinitionBundle(
            final RecordDefinitionRepository.RecordDefinitionFileBundle bundle) {
        try {
            recordDefinitionDeploymentService.deploySingleRecordDefinitionFile(
                    bundle.getRecordDefinitionFile());
            for (File file : bundle.getFormFiles()) {
                recordDefinitionDeploymentService.deploySingleRecordDefinitionFormFile(file);
            }
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }
}
