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

package org.mallfoundry.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@SpringBootTest
public class CustomerTests {

    @Autowired
    private InternalCustomerService customerService;

    @Test
    @Transactional
    @Rollback(false)
    public void testCreateCustomer() {

        InternalCustomer customer = new InternalCustomer();
        customer.setId("customer 1");
        customer.setGender(Gender.MALE);
        customer.setNickname("tang zhi");
        customer.setBirthday(new Date());
        customer.setUserId("user 1");
        customer.addShippingAddress(
                InternalShippingAddress.builder()
                        .firstName("治").lastName("唐")
                        .mobile("1888888888")
                        .zip("2500000")
                        .countryCode("86")
                        .location("001,220,3222,333")
                        .address("山东省 济南市 历城区023号")
                        .defaulted()
                        .build());
        this.customerService.saveCustomer(customer);
    }
}
