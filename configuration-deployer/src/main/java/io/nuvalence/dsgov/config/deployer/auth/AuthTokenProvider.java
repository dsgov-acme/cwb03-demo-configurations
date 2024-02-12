package io.nuvalence.dsgov.config.deployer.auth;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretVersionName;
import io.nuvalence.auth.token.SelfSignedTokenGenerator;
import io.nuvalence.auth.util.RsaKeyUtility;
import io.nuvalence.dsgov.config.deployer.DeployerConfiguration;
import io.nuvalence.dsgov.config.deployer.EnvironmentAware;
import io.nuvalence.dsgov.config.deployer.EnvironmentConfiguration;
import io.nuvalence.dsgov.config.deployer.util.JacocoIgnoreInGeneratedReport;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * Generates and caches self signed JWTs for accessing work-manager.
 */
@RequiredArgsConstructor
@Slf4j
@JacocoIgnoreInGeneratedReport(reason = "Methods have side effect that call GCP services.")
public class AuthTokenProvider implements EnvironmentAware {
    private final DeployerConfiguration configuration;
    private SelfSignedTokenGenerator generator;
    private String token;
    private Instant tokenLastGenerated = Instant.MIN;

    @Getter private String environment;

    /**
     * Updates the environment the tokens need to be generated for, recreating it's internal
     * token generator if necessary.
     *
     * @param environment environment name
     */
    public void setEnvironment(final String environment) {
        if (!environment.equals(this.environment)) {
            generator = createGenerator(environment);
        }

        this.environment = environment;
    }

    /**
     * Returns a cached JWT or generates a new one if there isn't an unexpired token in the cache.
     *
     * @return JWT as String
     */
    public String getToken() {
        if (token == null
                || tokenLastGenerated.isBefore(Instant.now().minus(Duration.ofMinutes(3)))) {
            generateToken();
        }

        return token;
    }

    private SelfSignedTokenGenerator createGenerator(final String environment) {
        final EnvironmentConfiguration environmentConfiguration =
                configuration
                        .getEnvironment(environment)
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "Environment ["
                                                        + environment
                                                        + "] does not exist."));
        final String key = getPrivateKeyForEnvironment(environmentConfiguration);

        try {
            return new SelfSignedTokenGenerator(
                    environmentConfiguration.getTokenIssuer(),
                    Duration.ofMinutes(5),
                    RsaKeyUtility.getPrivateKeyFromString(key));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateToken() {
        if (generator == null) {
            throw new IllegalStateException(
                    "Token Generator not configured. This is likely because the environment has not been set.");
        }
        token =
                generator.generateToken(
                        "configuration-deployer",
                        List.of("wm:transaction-config-admin", "ns:notification-admin"));
        tokenLastGenerated = Instant.now();
    }

    private String getPrivateKeyForEnvironment(
            final EnvironmentConfiguration environmentConfiguration) {
        try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
            SecretVersionName secretVersionName =
                    SecretVersionName.of(
                            environmentConfiguration.getGcpProjectId(),
                            environmentConfiguration.getTokenPrivateKeySecret(),
                            "latest");
            log.debug("Looking up secret: {}", secretVersionName.toString());
            AccessSecretVersionResponse response = client.accessSecretVersion(secretVersionName);
            return response.getPayload().getData().toStringUtf8();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
