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

package com.mallfoundry.customer.infrastructure.persistence.mybatis;

import com.mallfoundry.customer.domain.Customer;
import com.mallfoundry.customer.domain.CustomerAvatar;
import com.mallfoundry.customer.domain.CustomerRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepositoryMybatis extends CustomerRepositorySupport {

    private final CustomerMapper customerMapper;

    public CustomerRepositoryMybatis(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Override
    protected void doAdd(Customer customer) {
        this.customerMapper.insert(customer);
    }

    @Override
    protected void doDelete(String customerId) {
        this.customerMapper.deleteById(customerId);
    }

    @Override
    protected void doUpdate(Customer customer) {
        this.customerMapper.update(customer);
    }

    @Override
    protected void doUpdateAvatar(CustomerAvatar avatar) {
        this.customerMapper.updateAvatar(avatar);
    }

    @Override
    public Customer findById(String customerId) {
        return this.customerMapper.selectById(customerId);
    }
}
