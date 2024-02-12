package io.nuvalence.dsgov.config.deployer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.nuvalence.config.workmanager.client.ApiException;
import io.nuvalence.config.workmanager.client.generated.api.AdminApi;
import io.nuvalence.config.workmanager.client.generated.models.FormConfigurationCreateModel;
import io.nuvalence.config.workmanager.client.generated.models.FormConfigurationResponseModel;
import io.nuvalence.config.workmanager.client.generated.models.FormConfigurationUpdateModel;
import io.nuvalence.config.workmanager.client.generated.models.TransactionDefinitionCreateModel;
import io.nuvalence.config.workmanager.client.generated.models.TransactionDefinitionResponseModel;
import io.nuvalence.config.workmanager.client.generated.models.TransactionDefinitionUpdateModel;
import io.nuvalence.dsgov.config.deployer.client.WorkManagerAdminClientFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Handles deployment of transactions and related forms.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionDeploymentService {
    private final WorkManagerAdminClientFactory clientFactory;

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    private final String formKeyAttribute = "key";

    /**
     * Deploys a single transaction file to work-manager.
     *
     * @param file transaction file (YAML) to deploy
     *
     * @throws IOException if the resource cannot be mapped to JsonNode.
     * @throws ApiException In case of api call of failure.
     */
    public void deploySingleTransactionFile(final File file) throws IOException, ApiException {
        String fileName = file.getName();

        if (!fileName.matches("^[A-Z].*\\.yaml$")) {
            log.error(
                    "Transaction Definition file incorrectly named, please follow the naming "
                            + "'{Transaction Definition Key}.yaml'");
            return;
        }

        String key = fileName.replace(".yaml", "");

        JsonNode root = mapper.readTree(file);

        TransactionDefinitionCreateModel transactionDefinitionCreateModel =
                mapper.treeToValue(root, TransactionDefinitionCreateModel.class);

        AdminApi adminApi = clientFactory.create();

        TransactionDefinitionResponseModel result = null;

        try {
            result = adminApi.postTransactionDefinition(transactionDefinitionCreateModel);
        } catch (ApiException e) {
            if (e.getMessage().contains("call failed with: 409")) {
                if (root.has(formKeyAttribute)) {
                    ((ObjectNode) root).remove(formKeyAttribute);
                }

                TransactionDefinitionUpdateModel transactionDefinitionUpdateModel =
                        mapper.treeToValue(root, TransactionDefinitionUpdateModel.class);

                result = adminApi.putTransactionDefinition(key, transactionDefinitionUpdateModel);

            } else {
                throw e;
            }
        }

        log.info("Deploying {}", file.getName());

        log.debug("Obtained response {}", result);
    }

    /**
     * Deploys a single transaction form file to work-manager.
     *
     * @param file transaction form file (YAML) to deploy
     *
     * @throws IOException if the resource cannot be mapped to JsonNode.
     * @throws ApiException In case of api call of failure.
     */
    public void deploySingleTransactionFormFile(final File file) throws IOException, ApiException {
        String fileName = file.getName();

        if (!fileName.matches("^[A-Z][A-Za-z]*-form-[A-Z][A-Za-z]*\\.yaml$")) {
            log.error(
                    "Form Configurations file incorrectly named, please follow the naming "
                            + "'{Transaction Definition Key}-form-{Form Definition Key}.yaml'");
            return;
        }
        fileName = fileName.replace(".yaml", "");

        String[] fileNameParts = fileName.split("-form-");
        String transactionDefinitionKey = fileNameParts[0];
        String formKey = fileNameParts[1];

        JsonNode root = mapper.readTree(file);
        if (root.has("transactionDefinitionKey")) {
            ((ObjectNode) root).remove("transactionDefinitionKey");
        }

        FormConfigurationCreateModel formConfigurationCreateModel =
                mapper.treeToValue(root, FormConfigurationCreateModel.class);

        AdminApi adminApi = clientFactory.create();

        FormConfigurationResponseModel result = null;

        try {
            result =
                    adminApi.postFormConfiguration(
                            transactionDefinitionKey, formConfigurationCreateModel);
        } catch (ApiException e) {
            if (e.getMessage().contains("call failed with: 409")) {
                if (root.has(formKeyAttribute)) {
                    ((ObjectNode) root).remove(formKeyAttribute);
                }

                FormConfigurationUpdateModel formConfigurationUpdateModel =
                        mapper.treeToValue(root, FormConfigurationUpdateModel.class);

                result =
                        adminApi.putFormConfiguration(
                                transactionDefinitionKey, formKey, formConfigurationUpdateModel);
            } else {
                throw e;
            }
        }

        log.info("Deploying {}", file.getName());

        log.debug("Obtained response {}", result);
    }
}
