package io.nuvalence.dsgov.config.deployer;

import io.nuvalence.dsgov.config.deployer.util.JacocoIgnoreInGeneratedReport;
import lombok.Data;

import java.io.File;
import java.util.Map;
import java.util.Optional;

/**
 * Application configuration.
 */
@Data
@JacocoIgnoreInGeneratedReport(
        reason = "Simple configuration class that provides access to values in YAML file.")
public class DeployerConfiguration {
    private Map<String, EnvironmentConfiguration> environments;

    /**
     * Gets environment specific configuration for a given environment.
     *
     * @param environmentName Request environment name
     * @return Environment configuration
     */
    public Optional<EnvironmentConfiguration> getEnvironment(final String environmentName) {
        return Optional.ofNullable(environments.get(environmentName));
    }

    /**
     * Returns the root configuration directory.
     *
     * @return configuration directory as File
     */
    public File getConfigurationDirectory() {
        // Currently hardcoded to support future configurability with components coding to this
        // accessor.
        return new File("./data");
    }
}
