/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mallfoundry.store.domain.product;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import java.util.Objects;

public abstract class ProductRepositorySupport implements ProductRepository, ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    protected abstract void doAdd(Product product);

    protected abstract void doDelete(String id);

    protected abstract void doUpdate(Product product);

    protected abstract Product doFindById(String id);

    @Override
    public Product findById(String id) {
        return this.doFindById(id);
    }

    @Override
    public void add(Product product) {
        this.doAdd(product);
        // Publish product added event.
        this.applicationEventPublisher.publishEvent(new ProductAddedEvent(product));
    }

    @Override
    public void delete(String id) {
        Product product = this.findById(id);
        if (Objects.nonNull(product)) {
            this.doDelete(id);
            // Publish product deleted event.
            this.applicationEventPublisher.publishEvent(new ProductDeletedEvent(product));
        }
    }

    @Override
    public void update(Product product) {
        this.doUpdate(product);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
