package org.mallfoundry.plugins;

import org.springframework.data.util.CastUtils;

import java.util.List;
import java.util.stream.Collectors;

public class SimplePluginRegistry implements PluginRegistry {

    private final List<Plugin<?>> plugins;

    public SimplePluginRegistry(List<Plugin<?>> plugins) {
        this.plugins = plugins;
    }

    @Override
    public <S, T extends Plugin<S>> List<T> getPlugins(Class<T> clazz) {
        return CastUtils.cast(
                this.plugins.stream()
                        .filter(plugin -> clazz.isAssignableFrom(plugin.getClass()))
                        .collect(Collectors.toUnmodifiableList()));
    }
}
