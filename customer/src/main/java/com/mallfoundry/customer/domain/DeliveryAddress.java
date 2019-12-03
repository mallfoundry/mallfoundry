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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryAddress {

    private String customerId;

    private String phone;

    private String postalCode;

    private String address;

    private boolean defaultAddress;

    public boolean isDefault() {
        return defaultAddress;
    }

    public void setDefault(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}
