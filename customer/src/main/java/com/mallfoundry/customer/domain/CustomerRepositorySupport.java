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

public abstract class CustomerRepositorySupport implements CustomerRepository {

    protected abstract void doAdd(Customer customer);

    protected abstract void doDelete(String customerId);

    protected abstract void doUpdate(Customer customer);

    protected abstract void doUpdateAvatar(CustomerAvatar avatar);

    @Override
    public void add(Customer customer) {
        this.doAdd(customer);
    }

    @Override
    public void delete(String customerId) {
        this.doDelete(customerId);
    }

    @Override
    public void update(Customer customer) {
        this.doUpdate(customer);
    }

    @Override
    public void updateAvatar(CustomerAvatar avatar) {
        this.doUpdateAvatar(avatar);
    }

    @Override
    public Customer findById(String customerId) {
        return null;
    }
}
