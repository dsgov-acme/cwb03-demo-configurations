package io.nuvalence.dsgov.config.deployer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.nuvalence.config.workmanager.client.ApiException;
import io.nuvalence.dsgov.config.deployer.service.EmailLayoutService;
import io.nuvalence.dsgov.config.deployer.service.MessageTemplateService;
import io.nuvalence.dsgov.config.deployer.service.RecordDefinitionDeploymentService;
import io.nuvalence.dsgov.config.deployer.service.SchemaDeploymentService;
import io.nuvalence.dsgov.config.deployer.service.TransactionDefinitionSetDeploymentService;
import io.nuvalence.dsgov.config.deployer.service.TransactionDeploymentService;
import io.nuvalence.dsgov.config.deployer.service.WorkflowDeploymentService;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class DeployerTest {
    private static final String CONFIG_SET = "test-config-set";
    private static final String ENVIRONMENT = "local";
    private static final File SIMPLE_SCHEMA_FILE =
            FileUtils.toFile(
                    DeployerTest.class.getResource(
                            "/test-data/test-config-set/schema/SimpleSchema.yaml"));
    private static final File TEST_PROCESS_FILE =
            FileUtils.toFile(
                    DeployerTest.class.getResource(
                            "/test-data/test-config-set/workflow/TestProcess.bpmn"));
    private static final File TEST_DECISION_FILE =
            FileUtils.toFile(
                    DeployerTest.class.getResource(
                            "/test-data/test-config-set/workflow/TestDecision.dmn"));
    private static final File TEST_TRANSACTION_FILE =
            FileUtils.toFile(
                    DeployerTest.class.getResource(
                            "/test-data/test-config-set/transaction/TestTransaction.yaml"));
    private static final File DEFAULT_FORM_FILE =
            FileUtils.toFile(
                    DeployerTest.class.getResource(
                            "/test-data/test-config-set/transaction/TestTransaction-form-Default.yaml"));
    private static final File OTHER_FORM_FILE =
            FileUtils.toFile(
                    DeployerTest.class.getResource(
                            "/test-data/test-config-set/transaction/TestTransaction-form-Other.yaml"));

    private static final File EMAIL_LAYOUT_FILE =
            FileUtils.toFile(
                    DeployerTest.class.getResource(
                            "/test-data/test-config-set/notification/email-layout/TestEmailLayout.yaml"));

    private static final File MESSAGE_TEMPLATE_FILE =
            FileUtils.toFile(
                    DeployerTest.class.getResource(
                            "/test-data/test-config-set/notification/message-template/TestMessageTemplate.yaml"));

    private static final File TRANSACTION_DEFINITION_SET_FILE =
            FileUtils.toFile(
                    DeployerTest.class.getResource(
                            "/test-data/test-config-set/transaction-set/SimpleSet.yaml"));

    private static final File TRANSACTION_DEFINITION_SET_ORDER_FILE =
            FileUtils.toFile(
                    DeployerTest.class.getResource("/test-data/test-config-set/Dashboards.yaml"));

    private static final File TEST_RECORD_DEFINITION_FILE =
            FileUtils.toFile(
                    DeployerTest.class.getResource(
                            "/test-data/test-config-set/record-definition/TestRecordDefinition.yaml"));

    private static final File RECORD_DEFINTION_DEFAULT_FORM_FILE =
            FileUtils.toFile(
                    DeployerTest.class.getResource(
                            "/test-data/test-config-set/record-definition/TestRecordDefinition-form-Default.yaml"));
    private static final File RECORD_DEFINTION_OTHER_FORM_FILE =
            FileUtils.toFile(
                    DeployerTest.class.getResource(
                            "/test-data/test-config-set/record-definition/TestRecordDefinition-form-Other.yaml"));

    private Deployer deployer;
    private DeployerConfiguration configuration;
    private EnvironmentAwareContext environmentAwareContext;
    private WorkflowDeploymentService workflowDeploymentService;
    private SchemaDeploymentService schemaDeploymentService;
    private TransactionDeploymentService transactionDeploymentService;
    private EmailLayoutService emailLayoutService;

    private MessageTemplateService messageTemplateService;
    private TransactionDefinitionSetDeploymentService transactionDefinitionSetDeploymentService;
    private RecordDefinitionDeploymentService recordDefinitionDeploymentService;

    @BeforeEach
    void setUp() {
        configuration = mock(DeployerConfiguration.class);
        environmentAwareContext = mock(EnvironmentAwareContext.class);
        workflowDeploymentService = mock(WorkflowDeploymentService.class);
        schemaDeploymentService = mock(SchemaDeploymentService.class);
        transactionDeploymentService = mock(TransactionDeploymentService.class);
        emailLayoutService = mock(EmailLayoutService.class);
        messageTemplateService = mock(MessageTemplateService.class);
        transactionDefinitionSetDeploymentService =
                mock(TransactionDefinitionSetDeploymentService.class);
        recordDefinitionDeploymentService = mock(RecordDefinitionDeploymentService.class);

        deployer =
                new Deployer(
                        configuration,
                        environmentAwareContext,
                        workflowDeploymentService,
                        schemaDeploymentService,
                        transactionDeploymentService,
                        emailLayoutService,
                        messageTemplateService,
                        transactionDefinitionSetDeploymentService,
                        recordDefinitionDeploymentService);

        when(configuration.getConfigurationDirectory())
                .thenReturn(FileUtils.toFile(getClass().getResource("/test-data")));
    }

    @Test
    void deployAll()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deployAll(CONFIG_SET, ENVIRONMENT);

        verify(schemaDeploymentService, times(1)).deploySingleSchemaFile(any());
        verify(schemaDeploymentService, times(1)).deploySingleSchemaFile(SIMPLE_SCHEMA_FILE);

        verify(workflowDeploymentService, times(1)).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, times(1)).deploySingleWorkflowFile(TEST_PROCESS_FILE);

        verify(workflowDeploymentService, times(1)).deploySingleDecisionTableFile(any());
        verify(workflowDeploymentService, times(1))
                .deploySingleDecisionTableFile(TEST_DECISION_FILE);

        verify(transactionDeploymentService, times(1)).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, times(1))
                .deploySingleTransactionFile(TEST_TRANSACTION_FILE);

        verify(transactionDeploymentService, times(2)).deploySingleTransactionFormFile(any());
        verify(transactionDeploymentService, times(1))
                .deploySingleTransactionFormFile(DEFAULT_FORM_FILE);
        verify(transactionDeploymentService, times(1))
                .deploySingleTransactionFormFile(OTHER_FORM_FILE);

        verify(emailLayoutService, times(1)).deploySingleEmailLayout(EMAIL_LAYOUT_FILE);
        verify(messageTemplateService, times(1)).deploySingleMessageTemplate(MESSAGE_TEMPLATE_FILE);

        verify(transactionDefinitionSetDeploymentService, times(1))
                .deploySingleTransactionDefinitionSet(any());
        verify(transactionDefinitionSetDeploymentService, times(1))
                .deploySingleTransactionDefinitionSet(TRANSACTION_DEFINITION_SET_FILE);

        verify(transactionDefinitionSetDeploymentService, times(1))
                .deployTransactionDefinitionSetOrder(any());
        verify(transactionDefinitionSetDeploymentService, times(1))
                .deployTransactionDefinitionSetOrder(TRANSACTION_DEFINITION_SET_ORDER_FILE);

        verify(recordDefinitionDeploymentService, times(1)).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, times(1))
                .deploySingleRecordDefinitionFile(TEST_RECORD_DEFINITION_FILE);

        verify(recordDefinitionDeploymentService, times(2))
                .deploySingleRecordDefinitionFormFile(any());
        verify(recordDefinitionDeploymentService, times(1))
                .deploySingleRecordDefinitionFormFile(RECORD_DEFINTION_DEFAULT_FORM_FILE);
        verify(recordDefinitionDeploymentService, times(1))
                .deploySingleRecordDefinitionFormFile(RECORD_DEFINTION_OTHER_FORM_FILE);
    }

    @Test
    void deployType_EmailLayout()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deployType(CONFIG_SET, ENVIRONMENT, "email-layout");

        verify(emailLayoutService, times(1)).deploySingleEmailLayout(any());
        verify(emailLayoutService, times(1)).deploySingleEmailLayout(EMAIL_LAYOUT_FILE);

        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deployTransactionDefinitionSetOrder(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deployType_MessageTemplate()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deployType(CONFIG_SET, ENVIRONMENT, "message-template");

        verify(messageTemplateService, times(1)).deploySingleMessageTemplate(any());
        verify(messageTemplateService, times(1)).deploySingleMessageTemplate(MESSAGE_TEMPLATE_FILE);

        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(emailLayoutService, never()).deploySingleEmailLayout(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deployTransactionDefinitionSetOrder(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deployType_Workflow()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deployType(CONFIG_SET, ENVIRONMENT, "workflow");

        verify(workflowDeploymentService, times(1)).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, times(1)).deploySingleWorkflowFile(TEST_PROCESS_FILE);

        verify(workflowDeploymentService, times(1)).deploySingleDecisionTableFile(any());
        verify(workflowDeploymentService, times(1))
                .deploySingleDecisionTableFile(TEST_DECISION_FILE);

        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(emailLayoutService, never()).deploySingleEmailLayout(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deployTransactionDefinitionSetOrder(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deployType_Schema()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deployType(CONFIG_SET, ENVIRONMENT, "schema");

        verify(schemaDeploymentService, times(1)).deploySingleSchemaFile(any());
        verify(schemaDeploymentService, times(1)).deploySingleSchemaFile(SIMPLE_SCHEMA_FILE);

        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(emailLayoutService, never()).deploySingleEmailLayout(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deployTransactionDefinitionSetOrder(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deployType_Transaction()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deployType(CONFIG_SET, ENVIRONMENT, "transaction");

        verify(transactionDeploymentService, times(1)).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, times(1))
                .deploySingleTransactionFile(TEST_TRANSACTION_FILE);

        verify(transactionDeploymentService, times(2)).deploySingleTransactionFormFile(any());
        verify(transactionDeploymentService, times(1))
                .deploySingleTransactionFormFile(DEFAULT_FORM_FILE);
        verify(transactionDeploymentService, times(1))
                .deploySingleTransactionFormFile(OTHER_FORM_FILE);

        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(emailLayoutService, never()).deploySingleEmailLayout(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deployTransactionDefinitionSetOrder(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deployType_TransactionDefinitionSet()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deployType(CONFIG_SET, ENVIRONMENT, "transaction-definition-set");

        verify(transactionDefinitionSetDeploymentService, times(1))
                .deploySingleTransactionDefinitionSet(any());
        verify(transactionDefinitionSetDeploymentService, times(1))
                .deploySingleTransactionDefinitionSet(TRANSACTION_DEFINITION_SET_FILE);

        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(emailLayoutService, never()).deploySingleEmailLayout(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deployTransactionDefinitionSetOrder(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deployType_TransactionDefinitionSetOrder()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deployType(CONFIG_SET, ENVIRONMENT, "transaction-definition-set-order");

        verify(transactionDefinitionSetDeploymentService, times(1))
                .deployTransactionDefinitionSetOrder(any());
        verify(transactionDefinitionSetDeploymentService, times(1))
                .deployTransactionDefinitionSetOrder(TRANSACTION_DEFINITION_SET_ORDER_FILE);

        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(emailLayoutService, never()).deploySingleEmailLayout(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deployType_RecordDefinition()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deployType(CONFIG_SET, ENVIRONMENT, "record-definition");

        verify(recordDefinitionDeploymentService, times(1)).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, times(1))
                .deploySingleRecordDefinitionFile(TEST_RECORD_DEFINITION_FILE);

        verify(recordDefinitionDeploymentService, times(2))
                .deploySingleRecordDefinitionFormFile(any());
        verify(recordDefinitionDeploymentService, times(1))
                .deploySingleRecordDefinitionFormFile(RECORD_DEFINTION_DEFAULT_FORM_FILE);
        verify(recordDefinitionDeploymentService, times(1))
                .deploySingleRecordDefinitionFormFile(RECORD_DEFINTION_OTHER_FORM_FILE);

        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
    }

    @Test
    void deploySingle_EmailLayout()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deploySingle(CONFIG_SET, ENVIRONMENT, "email-layout", "TestEmailLayout");

        verify(emailLayoutService, times(1)).deploySingleEmailLayout(any());
        verify(emailLayoutService, times(1)).deploySingleEmailLayout(EMAIL_LAYOUT_FILE);

        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deploySingle_MessageTemplate()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deploySingle(CONFIG_SET, ENVIRONMENT, "message-template", "TestMessageTemplate");

        verify(messageTemplateService, times(1)).deploySingleMessageTemplate(any());
        verify(messageTemplateService, times(1)).deploySingleMessageTemplate(MESSAGE_TEMPLATE_FILE);

        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(emailLayoutService, never()).deploySingleEmailLayout(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deploySingle_Workflow()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deploySingle(CONFIG_SET, ENVIRONMENT, "workflow", "TestProcess");

        verify(workflowDeploymentService, times(1)).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, times(1)).deploySingleWorkflowFile(TEST_PROCESS_FILE);

        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(emailLayoutService, never()).deploySingleEmailLayout(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deploySingle_Schema()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deploySingle(CONFIG_SET, ENVIRONMENT, "schema", "SimpleSchema");

        verify(schemaDeploymentService, times(1)).deploySingleSchemaFile(any());
        verify(schemaDeploymentService, times(1)).deploySingleSchemaFile(SIMPLE_SCHEMA_FILE);

        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(emailLayoutService, never()).deploySingleEmailLayout(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deploySingle_Transaction()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deploySingle(CONFIG_SET, ENVIRONMENT, "transaction", "TestTransaction");

        verify(transactionDeploymentService, times(1)).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, times(1))
                .deploySingleTransactionFile(TEST_TRANSACTION_FILE);

        verify(transactionDeploymentService, times(2)).deploySingleTransactionFormFile(any());
        verify(transactionDeploymentService, times(1))
                .deploySingleTransactionFormFile(DEFAULT_FORM_FILE);
        verify(transactionDeploymentService, times(1))
                .deploySingleTransactionFormFile(OTHER_FORM_FILE);

        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(emailLayoutService, never()).deploySingleEmailLayout(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deploySingle_TransactionDefinitionSet()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deploySingle(CONFIG_SET, ENVIRONMENT, "transaction-definition-set", "SimpleSet");

        verify(transactionDefinitionSetDeploymentService, times(1))
                .deploySingleTransactionDefinitionSet(any());
        verify(transactionDefinitionSetDeploymentService, times(1))
                .deploySingleTransactionDefinitionSet(TRANSACTION_DEFINITION_SET_FILE);

        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(emailLayoutService, never()).deploySingleEmailLayout(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deploySingle_RecordDefinition()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deploySingle(CONFIG_SET, ENVIRONMENT, "record-definition", "TestRecordDefinition");

        verify(recordDefinitionDeploymentService, times(1)).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, times(1))
                .deploySingleRecordDefinitionFile(TEST_RECORD_DEFINITION_FILE);

        verify(recordDefinitionDeploymentService, times(2))
                .deploySingleRecordDefinitionFormFile(any());
        verify(recordDefinitionDeploymentService, times(1))
                .deploySingleRecordDefinitionFormFile(RECORD_DEFINTION_DEFAULT_FORM_FILE);
        verify(recordDefinitionDeploymentService, times(1))
                .deploySingleRecordDefinitionFormFile(RECORD_DEFINTION_OTHER_FORM_FILE);

        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
    }

    @Test
    void deploySingle_EmailLayout_NotFound()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deploySingle(CONFIG_SET, ENVIRONMENT, "email-layout", "Missing");

        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deploySingle_MessageTemplate_NotFound()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deploySingle(CONFIG_SET, ENVIRONMENT, "message-template", "Missing");

        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(emailLayoutService, never()).deploySingleEmailLayout(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deploySingle_Workflow_NotFound()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deploySingle(CONFIG_SET, ENVIRONMENT, "workflow", "Missing");

        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(emailLayoutService, never()).deploySingleEmailLayout(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deploySingle_Schema_NotFound()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deploySingle(CONFIG_SET, ENVIRONMENT, "schema", "Missing");

        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(emailLayoutService, never()).deploySingleEmailLayout(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deploySingle_Transaction_NotFound()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deploySingle(CONFIG_SET, ENVIRONMENT, "transaction", "Missing");

        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(emailLayoutService, never()).deploySingleEmailLayout(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deploySingle_TransactionDefinitionSet_NotFound()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deploySingle(CONFIG_SET, ENVIRONMENT, "transaction-definition-set", "Missing");

        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(emailLayoutService, never()).deploySingleEmailLayout(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }

    @Test
    void deploySingle_RecordDefinition_NotFound()
            throws IOException, ApiException, io.nuvalence.config.notification.client.ApiException {
        deployer.deploySingle(CONFIG_SET, ENVIRONMENT, "record-definition", "Missing");

        verify(schemaDeploymentService, never()).deploySingleSchemaFile(any());
        verify(workflowDeploymentService, never()).deploySingleWorkflowFile(any());
        verify(workflowDeploymentService, never()).deploySingleDecisionTableFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFile(any());
        verify(transactionDeploymentService, never()).deploySingleTransactionFormFile(any());
        verify(emailLayoutService, never()).deploySingleEmailLayout(any());
        verify(messageTemplateService, never()).deploySingleMessageTemplate(any());
        verify(transactionDefinitionSetDeploymentService, never())
                .deploySingleTransactionDefinitionSet(any());
        verify(recordDefinitionDeploymentService, never()).deploySingleRecordDefinitionFile(any());
        verify(recordDefinitionDeploymentService, never())
                .deploySingleRecordDefinitionFormFile(any());
    }
}
