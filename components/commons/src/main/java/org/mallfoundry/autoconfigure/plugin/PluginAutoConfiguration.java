package org.mallfoundry.autoconfigure.plugin;

import org.mallfoundry.plugins.Plugin;
import org.mallfoundry.plugins.PluginRegistry;
import org.mallfoundry.plugins.SimplePluginRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PluginAutoConfiguration {

    @Bean
    @Autowired(required = false)
    public PluginRegistry pluginRegistry(List<Plugin<?>> plugins) {
        return new SimplePluginRegistry(plugins);
    }
}
