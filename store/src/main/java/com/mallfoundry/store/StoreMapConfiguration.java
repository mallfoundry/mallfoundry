package com.mallfoundry.store;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StoreMapConfiguration extends ConcurrentHashMap<String, String> implements StoreConfiguration {

    public StoreMapConfiguration(Map<String, String> map) {
        this.putAll(map);
    }

    @Override
    public String get(String name) {
        return this.get((Object) name);
    }

    @Override
    public String get(String name, String defaultValue) {
        return this.getOrDefault(name, defaultValue);
    }

    @Transactional
    @Override
    public void set(String name, String value) {
        this.put(name, value);
    }

    @Transactional
    @Override
    public void unset(String name) {
        this.remove(name);
    }

    @Override
    public Map<String, String> toMap() {
        return Collections.unmodifiableMap(this);
    }
}
