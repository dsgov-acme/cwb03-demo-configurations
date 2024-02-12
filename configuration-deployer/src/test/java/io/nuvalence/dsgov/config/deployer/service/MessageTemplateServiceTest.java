package io.nuvalence.dsgov.config.deployer.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import io.nuvalence.config.notification.client.ApiException;
import io.nuvalence.config.notification.client.generated.api.AdminNotificationApi;
import io.nuvalence.config.notification.client.generated.models.TemplateResponseModel;
import io.nuvalence.dsgov.config.deployer.client.NotificationServiceAdminClientFactory;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.Objects;

public class MessageTemplateServiceTest {
    private MessageTemplateService service;
    private NotificationServiceAdminClientFactory clientFactory;

    @BeforeEach
    void setUp() {
        clientFactory = mock(NotificationServiceAdminClientFactory.class);
        clientFactory.setEnvironment("local");
        service = new MessageTemplateService(clientFactory);
    }

    @Test
    void deploySingleMessageTemplateTest() throws ApiException {
        final File file =
                FileUtils.toFile(
                        getClass()
                                .getResource(
                                        "/test-data/test-config-set/notification/message-template"
                                                + "/TestMessageTemplate.yaml"));

        AdminNotificationApi adminApi = Mockito.mock(AdminNotificationApi.class);

        when(clientFactory.create()).thenReturn(adminApi);
        when(adminApi.createTemplate(anyString(), any())).thenReturn(new TemplateResponseModel());

        assertDoesNotThrow(() -> service.deploySingleMessageTemplate(Objects.requireNonNull(file)));
        verify(adminApi).createTemplate(anyString(), any());
    }
}
