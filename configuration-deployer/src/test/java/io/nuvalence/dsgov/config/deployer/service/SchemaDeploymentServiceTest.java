package io.nuvalence.dsgov.config.deployer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import io.nuvalence.config.workmanager.client.ApiException;
import io.nuvalence.config.workmanager.client.generated.api.AdminApi;
import io.nuvalence.config.workmanager.client.generated.models.SchemaModel;
import io.nuvalence.dsgov.config.deployer.client.WorkManagerAdminClientFactory;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

class SchemaDeploymentServiceTest {
    private SchemaDeploymentService service;
    private WorkManagerAdminClientFactory clientFactory;

    @BeforeEach
    void setUp() {
        clientFactory = mock(WorkManagerAdminClientFactory.class);
        clientFactory.setEnvironment("local");
        service = new SchemaDeploymentService(clientFactory);
    }

    @Test
    void deploySingleSchemaFile_CreateSchema() throws ApiException {
        final File file =
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/schema/SimpleSchema.yaml"));
        AdminApi adminApi = Mockito.mock(AdminApi.class);

        when(clientFactory.create()).thenReturn(adminApi);
        when(adminApi.createSchema(any())).thenReturn(new SchemaModel());

        assertDoesNotThrow(() -> service.deploySingleSchemaFile(Objects.requireNonNull(file)));

        verify(adminApi).createSchema(any());

        verify(adminApi, never()).updateSchema(anyString(), any());
    }

    @Test
    void deploySingleSchemaFile_UpdateSchema() throws ApiException, IOException {
        final File file =
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/schema/SimpleSchema.yaml"));
        AdminApi adminApi = Mockito.mock(AdminApi.class);

        when(clientFactory.create()).thenReturn(adminApi);

        ApiException conflictException = new ApiException(409, "call failed with: 409");
        when(adminApi.createSchema(any())).thenThrow(conflictException);

        when(adminApi.updateSchema(anyString(), any())).thenReturn(new SchemaModel());

        service.deploySingleSchemaFile(Objects.requireNonNull(file));

        verify(adminApi).createSchema(any());

        verify(adminApi).updateSchema(anyString(), any());
    }
}
