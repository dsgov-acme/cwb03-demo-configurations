package io.nuvalence.dsgov.config.deployer.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import io.nuvalence.config.notification.client.ApiException;
import io.nuvalence.config.notification.client.generated.api.AdminNotificationApi;
import io.nuvalence.config.notification.client.generated.models.EmailLayoutResponseModel;
import io.nuvalence.dsgov.config.deployer.client.NotificationServiceAdminClientFactory;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.Objects;

public class EmailLayoutServiceTest {
    private EmailLayoutService service;
    private NotificationServiceAdminClientFactory clientFactory;

    @BeforeEach
    void setUp() {
        clientFactory = mock(NotificationServiceAdminClientFactory.class);
        clientFactory.setEnvironment("local");
        service = new EmailLayoutService(clientFactory);
    }

    @Test
    void deploySingleEmailLayoutTest() throws ApiException {
        final File file =
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/notification/email-layout/TestEmailLayout.yaml"));

        AdminNotificationApi adminApi = Mockito.mock(AdminNotificationApi.class);

        when(clientFactory.create()).thenReturn(adminApi);
        when(adminApi.createEmailLayout(anyString(), any()))
                .thenReturn(new EmailLayoutResponseModel());

        assertDoesNotThrow(() -> service.deploySingleEmailLayout(Objects.requireNonNull(file)));
        verify(adminApi).createEmailLayout(anyString(), any());
    }
}
