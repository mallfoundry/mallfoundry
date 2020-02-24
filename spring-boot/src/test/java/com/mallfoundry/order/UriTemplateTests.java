/*
 * Copyright 2020 the original author or authors.
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

package com.mallfoundry.order;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class UriTemplateTests {

    public static void main(String[] args) {

        String uri = UriComponentsBuilder.fromHttpUrl("http://29nr517912.zicp.vip/v1/payment_orders/{id}/confirm_payment")
                .build(Map.of("id", "111")).toString();
        System.out.println(uri);
    }
}
