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
import io.nuvalence.config.workmanager.client.generated.models.RecordDefinitionCreateModel;
import io.nuvalence.config.workmanager.client.generated.models.RecordDefinitionResponseModel;
import io.nuvalence.config.workmanager.client.generated.models.RecordDefinitionUpdateModel;
import io.nuvalence.dsgov.config.deployer.client.WorkManagerAdminClientFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Handles deployment of record definitions.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RecordDefinitionDeploymentService {

    private final WorkManagerAdminClientFactory clientFactory;

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    private final String formKeyAttribute = "key";

    /**
     * Deploys a single record definition file to work-manager.
     *
     * @param file record definition file (YAML) to deploy
     *
     * @throws IOException if the resource cannot be mapped to JsonNode.
     * @throws ApiException In case of api call of failure.
     */
    public void deploySingleRecordDefinitionFile(final File file) throws IOException, ApiException {
        String fileName = file.getName().substring(file.getName().indexOf('-') + 1);
        if (!fileName.matches("^[A-Z].*\\.yaml$")) {
            log.error(
                    "Schema Definition file incorrectly named, please follow the naming '{Schema Key}.yaml'");
            return;
        }
        String key = fileName.replace(".yaml", "");

        JsonNode root = mapper.readTree(file);

        RecordDefinitionCreateModel recordDefinitionCreateModel =
                mapper.treeToValue(root, RecordDefinitionCreateModel.class);

        AdminApi adminApi = clientFactory.create();

        RecordDefinitionResponseModel result = null;

        try {
            result = adminApi.postRecordDefinition(recordDefinitionCreateModel);
        } catch (ApiException e) {
            if (e.getMessage().contains("call failed with: 409")) {
                if (root.has(formKeyAttribute)) {
                    ((ObjectNode) root).remove(formKeyAttribute);
                }

                RecordDefinitionUpdateModel recordDefinitionUpdateModel =
                        mapper.treeToValue(root, RecordDefinitionUpdateModel.class);

                result = adminApi.putRecordDefinition(key, recordDefinitionUpdateModel);
            } else {
                throw e;
            }
        }

        log.info("Deploying {}", file.getName());

        log.debug("Obtained response {}", result);
    }

    /**
     * Deploys a single record definition form file to work-manager.
     *
     * @param file record definition for file (YAML) to deploy
     *
     * @throws IOException if the resource cannot be mapped to JsonNode.
     * @throws ApiException In case of api call of failure.
     */
    public void deploySingleRecordDefinitionFormFile(final File file)
            throws IOException, ApiException {
        String fileName = file.getName();

        if (!fileName.matches("^[A-Z][A-Za-z]*-form-[A-Z][A-Za-z]*\\.yaml$")) {
            log.error(
                    "Form Configurations file incorrectly named, please follow the naming "
                            + "'{Record Definition Key}-form-{Form Definition Key}.yaml'");
            return;
        }
        fileName = fileName.replace(".yaml", "");

        String[] fileNameParts = fileName.split("-form-");
        String recordDefinitionKey = fileNameParts[0];
        String formKey = fileNameParts[1];

        JsonNode root = mapper.readTree(file);
        if (root.has("recordDefinitionKey")) {
            ((ObjectNode) root).remove("recordDefinitionKey");
        }

        FormConfigurationCreateModel formConfigurationCreateModel =
                mapper.treeToValue(root, FormConfigurationCreateModel.class);

        AdminApi adminApi = clientFactory.create();

        FormConfigurationResponseModel result = null;

        try {
            result =
                    adminApi.postRecordDefinitionFormConfiguration(
                            recordDefinitionKey, formConfigurationCreateModel);
        } catch (ApiException e) {
            if (e.getMessage().contains("call failed with: 409")) {
                if (root.has(formKeyAttribute)) {
                    ((ObjectNode) root).remove(formKeyAttribute);
                }

                FormConfigurationUpdateModel formConfigurationUpdateModel =
                        mapper.treeToValue(root, FormConfigurationUpdateModel.class);

                result =
                        adminApi.putRecordDefinitionFormConfiguration(
                                recordDefinitionKey, formKey, formConfigurationUpdateModel);
            } else {
                throw e;
            }
        }

        log.info("Deploying {}", file.getName());

        log.debug("Obtained response {}", result);
    }
}
