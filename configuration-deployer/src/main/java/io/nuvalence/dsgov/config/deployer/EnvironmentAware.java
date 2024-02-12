package io.nuvalence.dsgov.config.deployer;

/**
 * Marks a component that receives notice when a target environment is selected.
 */
public interface EnvironmentAware {
    void setEnvironment(String environment);
}
