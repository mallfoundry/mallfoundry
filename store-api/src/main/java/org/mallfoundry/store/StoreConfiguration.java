package org.mallfoundry.store;

import java.util.Map;

public interface StoreConfiguration {

    String get(String name);

    String get(String name, String defaultValue);

    void set(String name, String value);

    void remove(String name);

    void clear();

    Map<String, String> toMap();
}
