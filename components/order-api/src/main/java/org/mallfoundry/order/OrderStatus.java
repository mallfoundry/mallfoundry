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

package org.mallfoundry.order;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {

    INCOMPLETE /* 准备下单 */,
    PENDING /* 开始结算流程 */,
    AWAITING_PAYMENT /* 等待付款 */,
    AWAITING_FULFILLMENT /* 等待打包 */,
    AWAITING_SHIPMENT /* 等待揽收 */,
    PARTIALLY_SHIPPED /* 部分已发货 */,
    SHIPPED /* 已发货 */,
    AWAITING_PICKUP /* 等待收货 */,

    PARTIALLY_REFUNDED /* 部分已退款 */,
    REFUNDED /* 已退款 */,

    VERIFICATION_REQUIRED /* 需要人工验证 */,
    DISPUTED /* 有争议的 */,
    CANCELLED /* 已取消 */,
    COMPLETED /* 完成 */,
    DECLINED /* 已拒绝 */;

    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
