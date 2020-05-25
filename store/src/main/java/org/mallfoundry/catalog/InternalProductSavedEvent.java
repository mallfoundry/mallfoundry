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

package org.mallfoundry.catalog;

import org.mallfoundry.catalog.Product;
import org.mallfoundry.catalog.ProductSavedEvent;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class InternalProductSavedEvent extends ApplicationEvent implements ProductSavedEvent {

    @Getter
    private final Product product;

    public InternalProductSavedEvent(Product product) {
        super(product);
        this.product = product;
    }
}
