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

package com.mallfoundry.customer.domain;

import java.util.List;

public abstract class BrowsingProductRepositorySupport implements BrowsingProductRepository {

    protected abstract void doAdd(BrowsingProduct browsingProduct);

    protected abstract void doDelete(BrowsingProduct browsingProduct);

    protected abstract void doDelete(List<BrowsingProduct> browsingProducts);

    protected abstract void doUpdate(BrowsingProduct browsingProduct);

    @Override
    public void add(BrowsingProduct browsingProduct) {
        this.doAdd(browsingProduct);
    }

    @Override
    public void delete(BrowsingProduct browsingProduct) {
        this.doDelete(browsingProduct);
    }

    @Override
    public void delete(List<BrowsingProduct> browsingProducts) {
        this.doDelete(browsingProducts);
    }

    @Override
    public void update(BrowsingProduct browsingProduct) {
        this.doUpdate(browsingProduct);
    }
}
