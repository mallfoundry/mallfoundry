/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.autoconfigure.thirdpay;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Getter
@Setter
@ConfigurationProperties("mallfoundry.thirdpay")
public class ThirdpayProperties {

    @NestedConfigurationProperty
    private Alipay alipay = new Alipay();

    @Getter
    @Setter
    static class Alipay {

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
}
