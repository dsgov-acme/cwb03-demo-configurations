package io.nuvalence.dsgov.config.deployer;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Configuration values associated with a specific DSGov environment.
 */
@Data
@NoArgsConstructor
public class EnvironmentConfiguration {
    private String gcpProjectId;
    private String tokenPrivateKeySecret;
    private String tokenIssuer;
    private String workManagerBaseUrl;
    private String notificationServiceBaseUrl;
}
