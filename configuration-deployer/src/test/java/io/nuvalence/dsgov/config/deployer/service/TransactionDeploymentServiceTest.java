package io.nuvalence.dsgov.config.deployer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import io.nuvalence.config.workmanager.client.ApiException;
import io.nuvalence.config.workmanager.client.generated.api.AdminApi;
import io.nuvalence.config.workmanager.client.generated.models.FormConfigurationResponseModel;
import io.nuvalence.config.workmanager.client.generated.models.TransactionDefinitionResponseModel;
import io.nuvalence.dsgov.config.deployer.client.WorkManagerAdminClientFactory;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

class TransactionDeploymentServiceTest {
    private TransactionDeploymentService service;
    private WorkManagerAdminClientFactory clientFactory;

    @BeforeEach
    void setUp() {
        clientFactory = mock(WorkManagerAdminClientFactory.class);
        service = new TransactionDeploymentService(clientFactory);
    }

    @Test
    void deploySingleTransactionFile_CreateTransactionDefinition() throws ApiException {
        final File file =
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/transaction/TestTransaction.yaml"));
        AdminApi adminApi = Mockito.mock(AdminApi.class);

        when(clientFactory.create()).thenReturn(adminApi);
        when(adminApi.postTransactionDefinition(any()))
                .thenReturn(new TransactionDefinitionResponseModel());

        assertDoesNotThrow(() -> service.deploySingleTransactionFile(Objects.requireNonNull(file)));

        verify(adminApi).postTransactionDefinition(any());

        verify(adminApi, never()).putTransactionDefinition(anyString(), any());
    }

    @Test
    void deploySingleTransactionFile_UpdateTransactionDefinition()
            throws ApiException, IOException {
        final File file =
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/transaction/TestTransaction.yaml"));
        AdminApi adminApi = Mockito.mock(AdminApi.class);

        when(clientFactory.create()).thenReturn(adminApi);

        ApiException conflictException = new ApiException(409, "call failed with: 409");
        when(adminApi.postTransactionDefinition(any())).thenThrow(conflictException);

        when(adminApi.putTransactionDefinition(anyString(), any()))
                .thenReturn(new TransactionDefinitionResponseModel());

        service.deploySingleTransactionFile(Objects.requireNonNull(file));

        verify(adminApi).postTransactionDefinition(any());

        verify(adminApi).putTransactionDefinition(anyString(), any());
    }

    @Test
    void deploySingleTransactionFormFile_PostFormConfiguration() throws ApiException {
        final File file =
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/transaction/TestTransaction-form-Default.yaml"));
        AdminApi adminApi = Mockito.mock(AdminApi.class);

        when(clientFactory.create()).thenReturn(adminApi);
        when(adminApi.postFormConfiguration(anyString(), any()))
                .thenReturn(new FormConfigurationResponseModel());

        assertDoesNotThrow(
                () -> service.deploySingleTransactionFormFile(Objects.requireNonNull(file)));

        verify(adminApi).postFormConfiguration(anyString(), any());
        verify(adminApi, never()).putFormConfiguration(anyString(), anyString(), any());
    }

    @Test
    void deploySingleTransactionFormFile_PutFormConfiguration() throws ApiException, IOException {
        final File file =
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/transaction/TestTransaction-form-Default.yaml"));
        AdminApi adminApi = Mockito.mock(AdminApi.class);

        when(clientFactory.create()).thenReturn(adminApi);

        ApiException conflictException = new ApiException(409, "call failed with: 409");
        when(adminApi.postFormConfiguration(anyString(), any())).thenThrow(conflictException);

        when(adminApi.putFormConfiguration(anyString(), anyString(), any()))
                .thenReturn(new FormConfigurationResponseModel());

        service.deploySingleTransactionFormFile(Objects.requireNonNull(file));

        verify(adminApi).postFormConfiguration(anyString(), any());
        verify(adminApi).putFormConfiguration(anyString(), anyString(), any());
    }
}
