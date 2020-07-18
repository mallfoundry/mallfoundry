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

package org.mallfoundry.checkout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.checkout.repository.jpa.convert.CheckoutItemListConverter;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderSource;
import org.mallfoundry.shipping.Address;
import org.mallfoundry.shipping.repository.jpa.convert.AddressConverter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_checkout")
public class InternalCheckout implements Checkout {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "cart_id_")
    private String cartId;

    @Column(name = "source_")
    private OrderSource source;

    @Column(name = "shipping_address_", length = 512)
    @Convert(converter = AddressConverter.class)
    private Address shippingAddress;

    @Column(name = "items_", length = 1024 * 2)
    @Convert(converter = CheckoutItemListConverter.class)
    private List<CheckoutItem> items = new ArrayList<>();

    @Transient
    private List<Order> orders = new ArrayList<>();

    @Column(name = "created_time_")
    private Date createdTime;

    public InternalCheckout(String id) {
        this.id = id;
    }

    public static InternalCheckout of(Checkout checkout) {
        if (checkout instanceof InternalCheckout) {
            return (InternalCheckout) checkout;
        }
        var target = new InternalCheckout();
        BeanUtils.copyProperties(checkout, target);
        return target;
    }

    @Override
    public CheckoutItem createItem() {
        return new InternalCheckoutItem();
    }

    @Override
    public void addItem(CheckoutItem item) {
        this.items.add(item);
    }

    @Override
    public BigDecimal getSubtotalAmount() {
        return this.getOrders().stream()
                .map(Order::getSubtotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void create() {
        this.createdTime = new Date();
    }
}
