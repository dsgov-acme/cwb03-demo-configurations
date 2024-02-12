package io.nuvalence.dsgov.config.deployer.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import io.nuvalence.config.workmanager.client.ApiException;
import io.nuvalence.config.workmanager.client.generated.api.AdminApi;
import io.nuvalence.config.workmanager.client.generated.models.TransactionDefinitionSetResponseModel;
import io.nuvalence.dsgov.config.deployer.client.WorkManagerAdminClientFactory;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TransactionDefinitionSetDeploymentServiceTest {
    private TransactionDefinitionSetDeploymentService service;
    private WorkManagerAdminClientFactory clientFactory;

    @BeforeEach
    void setUp() {
        clientFactory = mock(WorkManagerAdminClientFactory.class);
        clientFactory.setEnvironment("local");
        service = new TransactionDefinitionSetDeploymentService(clientFactory);
    }

    @Test
    void deploySingleTransactionDefinitionSet_CreateTransactionSet() throws ApiException {
        final File file =
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/transaction-set/SimpleSet.yaml"));
        AdminApi adminApi = Mockito.mock(AdminApi.class);

        when(clientFactory.create()).thenReturn(adminApi);
        when(adminApi.postTransactionSet(any()))
                .thenReturn(new TransactionDefinitionSetResponseModel());

        assertDoesNotThrow(
                () -> service.deploySingleTransactionDefinitionSet(Objects.requireNonNull(file)));

        verify(adminApi).postTransactionSet(any());

        verify(adminApi, never()).putTransactionSet(anyString(), any());
    }

    @Test
    void deploySingleTransactionDefinitionSet_UpdateTransactionSet()
            throws ApiException, IOException {
        final File file =
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/transaction-set/SimpleSet.yaml"));
        AdminApi adminApi = Mockito.mock(AdminApi.class);

        when(clientFactory.create()).thenReturn(adminApi);

        ApiException conflictException = new ApiException(409, "call failed with: 409");
        when(adminApi.postTransactionSet(any())).thenThrow(conflictException);

        when(adminApi.putTransactionSet(anyString(), any()))
                .thenReturn(new TransactionDefinitionSetResponseModel());

        service.deploySingleTransactionDefinitionSet(Objects.requireNonNull(file));

        verify(adminApi).postTransactionSet(any());

        verify(adminApi).putTransactionSet(anyString(), any());
    }

    @Test
    void deployTransactionDefinitionSetOrderTest() throws ApiException {
        final File file =
                FileUtils.toFile(
                        getClass().getResource("/test-data/test-config-set/Dashboards.yaml"));

        AdminApi adminApi = Mockito.mock(AdminApi.class);

        when(clientFactory.create()).thenReturn(adminApi);
        doNothing().when(adminApi).updateDashboardOrder(any());

        assertDoesNotThrow(
                () -> service.deployTransactionDefinitionSetOrder(Objects.requireNonNull(file)));

        verify(adminApi).updateDashboardOrder(any());
    }
}
