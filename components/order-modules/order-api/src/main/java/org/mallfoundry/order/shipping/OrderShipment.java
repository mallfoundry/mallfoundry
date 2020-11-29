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

package org.mallfoundry.order.shipping;

import org.mallfoundry.shipping.Address;
import org.mallfoundry.shipping.CarrierCode;
import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 订单物流信息对象。
 *
 * @author Zhi Tang
 */
public interface OrderShipment extends ObjectBuilder.ToBuilder<OrderShipment.Builder> {

    /**
     * 返回订单物流对象标识。
     */
    String getId();

    /**
     * 设置订单物流对象标识。
     */
    void setId(String id);

    /**
     * 返回订单对象标识。
     */
    String getOrderId();

    /**
     * 返回发货人对象标识。
     */
    String getConsignorId();

    /**
     * 设置发货人对象标识。
     */
    void setConsignorId(String consignorId);

    /**
     * 返回发货人对象名称。
     */
    String getConsignor();

    /**
     * 设置发货人对象名称。
     */
    void setConsignor(String consignor);

    /**
     * 发货物品图片集合，用于需要展示发货物品时使用。
     */
    List<String> getImageUrls();

    /**
     * 创建一个新的订单物流项对象。
     *
     * @param id 订单物流项标识，可以为 null
     */
    OrderShipmentItem createItem(String id);

    Optional<OrderShipmentItem> getItem(String id);

    void addItem(OrderShipmentItem item);

    void removeItem(OrderShipmentItem item);

    List<OrderShipmentItem> getItems();

    /**
     * 返回送货地址对象。
     */
    Address getShippingAddress();

    /**
     * 设置送货地址对象。
     */
    void setShippingAddress(Address shippingAddress);

    /**
     * 返回物流提供商编码。
     */
    CarrierCode getShippingProvider();

    /**
     * 设置物流提供商编码。
     */
    void setShippingProvider(CarrierCode shippingProvider);

    /**
     * 返回物流提供商名称。
     */
    String getTrackingCarrier();

    /**
     * 设置物流提供商名称。
     */
    void setTrackingCarrier(String trackingCarrier);

    /**
     * 返回物流配送方式。
     */
    String getShippingMethod();

    /**
     * 设置物流配送方式。
     */
    void setShippingMethod(String shippingMethod);

    /**
     * 返回物流运单号。
     */
    String getTrackingNumber();

    /**
     * 设置物流运单号。
     */
    void setTrackingNumber(String trackingNumber);

    /**
     * 返回发货时间。
     */
    Date getShippedTime();

    void ship();

    interface Builder extends ObjectBuilder<OrderShipment> {

        Builder items(List<OrderShipmentItem> items);

        Builder consignorId(String consignorId);

        Builder consignor(String consignor);

        Builder shippingAddress(Address shippingAddress);

        Builder shippingProvider(CarrierCode shippingProvider);

        Builder trackingCarrier(String trackingCarrier);

        Builder shippingMethod(String shippingMethod);

        Builder trackingNumber(String trackingNumber);
    }
}
