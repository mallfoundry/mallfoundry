package com.mallfoundry.rest.store;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    @Override
    public void set(String name, String value) {
        this.put(name, value);
    }

    @Override
    public void remove(String name) {
        this.remove((Object) name);
    }

    @Override
    public Map<String, String> toMap() {
        return Collections.unmodifiableMap(this);
    }
}
