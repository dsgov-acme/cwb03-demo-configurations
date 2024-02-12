package io.nuvalence.dsgov.config.deployer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

class EnvironmentAwareContextTest {

    @Test
    void propagatesSetEnvironmentCallsToAllRegistersComponents() {
        final EnvironmentAwareContext context = new EnvironmentAwareContext();
        final EnvironmentAware environmentAware1 = mock(EnvironmentAware.class);
        final EnvironmentAware environmentAware2 = mock(EnvironmentAware.class);
        context.registerComponent(environmentAware1);
        context.registerComponent(environmentAware2);

        context.setEnvironment("test");

        verify(environmentAware1, times(1)).setEnvironment("test");
        verify(environmentAware2, times(1)).setEnvironment("test");
    }
}
