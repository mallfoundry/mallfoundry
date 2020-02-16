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

package com.mallfoundry.payment.alipay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AliPaymentProperties {

    private String serverUrl;

    private String appId;

    private String alipayPublicKey;

    private String appPrivateKey;

    private String charset = "UTF-8";

    private String format = "json";

    private String signType = "RSA2";

    private String returnUrl;

    private String notifyUrl;
}
