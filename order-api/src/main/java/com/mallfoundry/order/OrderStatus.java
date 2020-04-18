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

package com.mallfoundry.order;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum OrderStatus {

    INCOMPLETE(1) /* 准备下单 */,
    PENDING(2) /* 开始结算流程 */,
    AWAITING_PAYMENT(3) /* 等待付款 */,
    AWAITING_FULFILLMENT(4) /* 等待打包 */,
    AWAITING_SHIPMENT(7) /* 等待揽收 */,
    PARTIALLY_SHIPPED(5) /* 部分已发货 */,
    SHIPPED(6) /* 已发货 */,
    AWAITING_PICKUP(8) /* 等待收货 */,
    VERIFICATION_REQUIRED(10) /* 需要人工验证 */,
    DISPUTED(11) /* 有争议的 */,
    PARTIALLY_REFUNDED(12) /* 部分已退款 */,

    REFUNDED(13) /* 已退款 */,
    CANCELLED(14) /* 已取消 */,
    COMPLETED(9) /* 完成 */,
    DECLINED(15) /* 已拒绝 */;

    @Getter
    private final int order;

    OrderStatus(int order) {
        this.order = order;
    }

    @JsonValue
    private String lowercase() {
        return this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return this.lowercase();
    }
}
