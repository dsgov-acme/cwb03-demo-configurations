package io.nuvalence.dsgov.config.deployer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.nuvalence.config.notification.client.ApiException;
import io.nuvalence.config.notification.client.generated.api.AdminNotificationApi;
import io.nuvalence.config.notification.client.generated.models.EmailLayoutRequestModel;
import io.nuvalence.dsgov.config.deployer.client.NotificationServiceAdminClientFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Handles deployment of email layouts.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EmailLayoutService {
    private final NotificationServiceAdminClientFactory clientFactory;

    /**
     * Deploys a single email layout file to notification-service.
     *
     * @param file schema file (YAML) to deploy
     *
     * @throws IOException if the resource cannot be mapped to JsonNode.
     * @throws ApiException In case of api call of failure.
     */
    public void deploySingleEmailLayout(final File file) throws IOException, ApiException {
        String fileName = file.getName();
        if (!fileName.matches("^[A-Z].*\\.yaml$")) {
            log.error(
                    "Email layout file incorrectly named, please follow the naming '{EmailLayoutKey}.yaml'");
            return;
        }
        String key = fileName.replace(".yaml", "");

        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        JsonNode root = mapper.readTree(file);
        if (root.has("key")) {
            ((ObjectNode) root).remove("key");
        }

        EmailLayoutRequestModel requestModel =
                mapper.treeToValue(root, EmailLayoutRequestModel.class);

        AdminNotificationApi adminNotificationApi = clientFactory.create();

        adminNotificationApi.createEmailLayout(key, requestModel);
    }
}
