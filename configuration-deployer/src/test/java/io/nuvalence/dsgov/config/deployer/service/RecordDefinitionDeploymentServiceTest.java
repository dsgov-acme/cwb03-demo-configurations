package io.nuvalence.dsgov.config.deployer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import io.nuvalence.config.workmanager.client.ApiException;
import io.nuvalence.config.workmanager.client.generated.api.AdminApi;
import io.nuvalence.config.workmanager.client.generated.models.FormConfigurationResponseModel;
import io.nuvalence.config.workmanager.client.generated.models.RecordDefinitionResponseModel;
import io.nuvalence.dsgov.config.deployer.client.WorkManagerAdminClientFactory;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

class RecordDefinitionDeploymentServiceTest {
    private RecordDefinitionDeploymentService service;
    private WorkManagerAdminClientFactory clientFactory;

    @BeforeEach
    void setUp() {
        clientFactory = mock(WorkManagerAdminClientFactory.class);
        service = new RecordDefinitionDeploymentService(clientFactory);
    }

    @Test
    void deploySingleRecordDefinitionFile_CreateRecordDefinition() throws ApiException {
        final File file =
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/record-definition/"
                                                + "TestRecordDefinition.yaml"));
        AdminApi adminApi = Mockito.mock(AdminApi.class);

        when(clientFactory.create()).thenReturn(adminApi);
        when(adminApi.postRecordDefinition(any())).thenReturn(new RecordDefinitionResponseModel());

        assertDoesNotThrow(
                () -> service.deploySingleRecordDefinitionFile(Objects.requireNonNull(file)));

        verify(adminApi).postRecordDefinition(any());

        verify(adminApi, never()).putRecordDefinition(anyString(), any());
    }

    @Test
    void deploySingleRecordDefinition_UpdateRecordDefinition() throws ApiException, IOException {
        final File file =
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/record-definition/TestRecordDefinition.yaml"));
        AdminApi adminApi = Mockito.mock(AdminApi.class);

        when(clientFactory.create()).thenReturn(adminApi);

        ApiException conflictException = new ApiException(409, "call failed with: 409");
        when(adminApi.postRecordDefinition(any())).thenThrow(conflictException);

        when(adminApi.putRecordDefinition(anyString(), any()))
                .thenReturn(new RecordDefinitionResponseModel());

        service.deploySingleRecordDefinitionFile(Objects.requireNonNull(file));

        verify(adminApi).postRecordDefinition(any());

        verify(adminApi).putRecordDefinition(anyString(), any());
    }

    @Test
    void deploySingleRecordDefinitionFormFile_PostFormConfiguration() throws ApiException {
        final File file =
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/record-definition/"
                                                + "TestRecordDefinition-form-Default.yaml"));
        AdminApi adminApi = Mockito.mock(AdminApi.class);

        when(clientFactory.create()).thenReturn(adminApi);
        when(adminApi.postRecordDefinitionFormConfiguration(anyString(), any()))
                .thenReturn(new FormConfigurationResponseModel());

        assertDoesNotThrow(
                () -> service.deploySingleRecordDefinitionFormFile(Objects.requireNonNull(file)));

        verify(adminApi).postRecordDefinitionFormConfiguration(anyString(), any());
        verify(adminApi, never())
                .putRecordDefinitionFormConfiguration(anyString(), anyString(), any());
    }

    @Test
    void deploySingleRecordDefinitionFormFile_PutFormConfiguration()
            throws ApiException, IOException {
        final File file =
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/record-definition/"
                                                + "TestRecordDefinition-form-Default.yaml"));
        AdminApi adminApi = Mockito.mock(AdminApi.class);

        when(clientFactory.create()).thenReturn(adminApi);

        ApiException conflictException = new ApiException(409, "call failed with: 409");
        when(adminApi.postRecordDefinitionFormConfiguration(anyString(), any()))
                .thenThrow(conflictException);

        when(adminApi.putRecordDefinitionFormConfiguration(anyString(), anyString(), any()))
                .thenReturn(new FormConfigurationResponseModel());

        service.deploySingleRecordDefinitionFormFile(Objects.requireNonNull(file));

        verify(adminApi).postRecordDefinitionFormConfiguration(anyString(), any());
        verify(adminApi).putRecordDefinitionFormConfiguration(anyString(), anyString(), any());
    }
}
