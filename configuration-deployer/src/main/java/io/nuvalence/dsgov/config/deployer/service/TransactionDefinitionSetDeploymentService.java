package io.nuvalence.dsgov.config.deployer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.nuvalence.config.workmanager.client.ApiException;
import io.nuvalence.config.workmanager.client.generated.api.AdminApi;
import io.nuvalence.config.workmanager.client.generated.models.TransactionDefinitionSetCreateModel;
import io.nuvalence.config.workmanager.client.generated.models.TransactionDefinitionSetUpdateModel;
import io.nuvalence.dsgov.config.deployer.client.WorkManagerAdminClientFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for deploying transaction definition sets.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionDefinitionSetDeploymentService {
    private final WorkManagerAdminClientFactory clientFactory;

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    /**
     * Deploys a single transaction definition set.
     *
     * @param file the transaction definition set file
     * @throws IOException  if the file cannot be read
     * @throws ApiException if the transaction definition set cannot be deployed
     */
    public void deploySingleTransactionDefinitionSet(final File file)
            throws IOException, ApiException {
        String fileName = file.getName();

        if (!fileName.matches("^[A-Z].*\\.yaml$")) {
            log.error(
                    "Transaction Definition Set file incorrectly named, please follow the naming "
                            + "'{Transaction Definition Set Key}.yaml'");
            return;
        }

        String key = fileName.replace(".yaml", "");

        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        JsonNode root = mapper.readTree(file);

        AdminApi adminApi = clientFactory.create();

        TransactionDefinitionSetCreateModel transactionDefinitionSetCreateModel =
                mapper.treeToValue(root, TransactionDefinitionSetCreateModel.class);

        try {
            adminApi.postTransactionSet(transactionDefinitionSetCreateModel);
        } catch (ApiException e) {
            if (e.getMessage().contains("call failed with: 409")) {
                if (root.has("key")) {
                    ((ObjectNode) root).remove("key");
                }
                TransactionDefinitionSetUpdateModel transactionDefinitionSetUpdateModel =
                        mapper.treeToValue(root, TransactionDefinitionSetUpdateModel.class);

                adminApi.putTransactionSet(key, transactionDefinitionSetUpdateModel);
            } else {
                throw e;
            }
        }
    }

    /**
     * Deploys a transaction definition set order.
     *
     * @param file the transaction definition set order file
     * @throws IOException  if the file cannot be read
     * @throws ApiException if the transaction definition set order cannot be deployed
     */
    public void deployTransactionDefinitionSetOrder(File file) throws IOException, ApiException {

        JsonNode root = mapper.readTree(file);

        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        List<String> order = mapper.treeToValue(root, ArrayList.class);

        AdminApi adminApi = clientFactory.create();
        adminApi.updateDashboardOrder(order);
    }
}
