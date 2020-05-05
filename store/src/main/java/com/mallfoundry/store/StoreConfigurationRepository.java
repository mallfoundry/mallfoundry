package com.mallfoundry.store;

import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StoreConfigurationRepository implements StoreConfiguration {

    @Setter
    private String storeId;

    private final StoreConfigPropertyRepository repository;

    public StoreConfigurationRepository(StoreConfigPropertyRepository repository) {
        this.repository = repository;
    }

    @Override
    public String get(String name) {
        return this.repository.findById(StoreConfigPropertyId.of(this.storeId, name)).orElseThrow().getValue();
    }

    @Override
    public String get(String name, String defaultValue) {
        var property = this.repository.findById(StoreConfigPropertyId.of(this.storeId, name)).orElse(null);
        return Objects.isNull(property) ? defaultValue : property.getValue();
    }

    @Transactional
    @Override
    public void set(String name, String value) {
        this.repository.save(new StoreConfigProperty(this.storeId, name, value));
    }

    @Transactional
    @Override
    public void unset(String name) {
        this.repository.deleteById(StoreConfigPropertyId.of(this.storeId, name));
    }

    @Override
    public Map<String, String> toMap() {
        return this.repository
                .findAllByStoreId(this.storeId)
                .stream()
                .collect(Collectors.toConcurrentMap(StoreConfigProperty::getName, StoreConfigProperty::getValue));
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private final StoreConfigurationRepository configuration;

        public Builder(StoreConfigurationRepository configuration) {
            this.configuration = configuration;
        }

        public Builder storeId(String storeId) {
            this.configuration.setStoreId(storeId);
            return this;
        }

        public StoreConfigurationRepository build() {
            return this.configuration;
        }
    }

}
