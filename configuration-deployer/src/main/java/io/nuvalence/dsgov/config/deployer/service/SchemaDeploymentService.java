package io.nuvalence.dsgov.config.deployer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.nuvalence.config.workmanager.client.ApiException;
import io.nuvalence.config.workmanager.client.generated.api.AdminApi;
import io.nuvalence.config.workmanager.client.generated.models.SchemaCreateModel;
import io.nuvalence.config.workmanager.client.generated.models.SchemaModel;
import io.nuvalence.config.workmanager.client.generated.models.SchemaUpdateModel;
import io.nuvalence.dsgov.config.deployer.client.WorkManagerAdminClientFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Handles deployment of schemas.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SchemaDeploymentService {

    private final WorkManagerAdminClientFactory clientFactory;

    /**
     * Deploys a single schema file to work-manager.
     *
     * @param file schema file (YAML) to deploy
     *
     * @throws IOException if the resource cannot be mapped to JsonNode.
     * @throws ApiException In case of api call of failure.
     */
    public void deploySingleSchemaFile(final File file) throws IOException, ApiException {
        String fileName = file.getName().substring(file.getName().indexOf('-') + 1);
        if (!fileName.matches("^[A-Z].*\\.yaml$")) {
            log.error(
                    "Schema Definition file incorrectly named, please follow the naming '{Schema Key}.yaml'");
            return;
        }
        String key = fileName.replace(".yaml", "");

        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        JsonNode root = mapper.readTree(file);

        SchemaCreateModel schemaCreateModel = mapper.treeToValue(root, SchemaCreateModel.class);

        AdminApi adminApi = clientFactory.create();

        SchemaModel schemaModel = null;

        try {
            schemaModel = adminApi.createSchema(schemaCreateModel);
        } catch (ApiException e) {
            if (e.getMessage().contains("call failed with: 409")) {
                if (root.has("key")) {
                    ((ObjectNode) root).remove("key");
                }
                SchemaUpdateModel schemaUpdateModel =
                        mapper.treeToValue(root, SchemaUpdateModel.class);
                schemaModel = adminApi.updateSchema(key, schemaUpdateModel);
            } else {
                throw e;
            }
        }

        log.info("Deploying {}", file.getName());

        log.debug("Obtained response {}", schemaModel);
    }
}
