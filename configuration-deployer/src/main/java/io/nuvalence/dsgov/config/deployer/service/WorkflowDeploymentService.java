package io.nuvalence.dsgov.config.deployer.service;

import io.nuvalence.dsgov.camunda.client.DeploymentAPI;
import io.nuvalence.dsgov.config.deployer.client.CamundaClientFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.OffsetDateTime;

/**
 * Handles deployment of workflows and related decision tables.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class WorkflowDeploymentService {
    private final CamundaClientFactory clientFactory;

    /**
     * Deploys a single workflow file to work-manager.
     *
     * @param file BPMN file to deploy
     */
    public void deploySingleWorkflowFile(final File file) {
        try {
            log.info("Deploying {}", file.getName());

            DeploymentAPI adminApi = clientFactory.create();
            adminApi.createDeployment(true, true, file.getName(), OffsetDateTime.now(), file);
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }

    /**
     * Deploys a single decision table file to work-manager.
     *
     * @param file DMN file to deploy
     */
    public void deploySingleDecisionTableFile(final File file) {
        try {
            log.info("Deploying {}", file.getName());

            DeploymentAPI adminApi = clientFactory.create();
            adminApi.createDeployment(true, true, file.getName(), OffsetDateTime.now(), file);
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
    }
}
