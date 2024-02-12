package io.nuvalence.dsgov.config.deployer;

import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds a list of EnvironmentAware components to coordinate update when a target environment is selected.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EnvironmentAwareContext {
    private final List<EnvironmentAware> components = new ArrayList<>();
    @Getter private String environment;

    public void setEnvironment(final String environment) {
        this.environment = environment;
        components.forEach(component -> component.setEnvironment(environment));
    }

    final void registerComponent(final EnvironmentAware component) {
        components.add(component);
    }
}
