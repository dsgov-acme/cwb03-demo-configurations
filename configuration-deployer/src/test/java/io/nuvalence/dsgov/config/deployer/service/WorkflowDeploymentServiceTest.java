package io.nuvalence.dsgov.config.deployer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.nuvalence.dsgov.camunda.client.DeploymentAPI;
import io.nuvalence.dsgov.config.deployer.client.CamundaClientFactory;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.OffsetDateTime;
import java.util.Objects;

class WorkflowDeploymentServiceTest {
    private WorkflowDeploymentService service;
    private CamundaClientFactory clientFactory;

    @BeforeEach
    void setUp() {
        clientFactory = mock(CamundaClientFactory.class);
        service = new WorkflowDeploymentService(clientFactory);
    }

    @Test
    void deploySingleWorkflowFile() {
        final File file =
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/workflow/TestProcess.bpmn"));

        DeploymentAPI deploymentAPI = mock(DeploymentAPI.class);
        when(clientFactory.create()).thenReturn(deploymentAPI);

        service.deploySingleWorkflowFile(file);

        verify(deploymentAPI)
                .createDeployment(
                        eq(true),
                        eq(true),
                        eq(file.getName()),
                        any(OffsetDateTime.class),
                        eq(file));
        assertDoesNotThrow(() -> service.deploySingleWorkflowFile(Objects.requireNonNull(file)));
    }

    @Test
    void deploySingleDecisionTableFile() {
        final File file =
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/workflow/TestDecision.dmn"));

        DeploymentAPI deploymentAPI = mock(DeploymentAPI.class);
        when(clientFactory.create()).thenReturn(deploymentAPI);

        service.deploySingleWorkflowFile(file);

        verify(deploymentAPI)
                .createDeployment(
                        eq(true),
                        eq(true),
                        eq(file.getName()),
                        any(OffsetDateTime.class),
                        eq(file));
        assertDoesNotThrow(
                () -> service.deploySingleDecisionTableFile(Objects.requireNonNull(file)));
    }
}
