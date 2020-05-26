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

package org.mallfoundry.payment;

import org.mallfoundry.util.Position;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentProvider implements Position {

    private String name;

    private PaymentProviderType type;

    private Integer position;

    public PaymentProvider(PaymentProviderType type, String name, Integer position) {
        this.name = name;
        this.type = type;
        this.position = position;
    }
}
