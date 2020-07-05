package org.mallfoundry.plugins;

import java.util.List;

public interface PluginRegistry {
    <S, T extends Plugin<S>> List<T> getPlugins(Class<T> clazz);
}
