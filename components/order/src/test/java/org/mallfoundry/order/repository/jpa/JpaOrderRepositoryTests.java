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

package org.mallfoundry.order.repository.jpa;

import org.junit.jupiter.api.Test;
import org.mallfoundry.catalog.DefaultOptionSelection;
import org.mallfoundry.order.OrderSource;
import org.mallfoundry.shipping.DefaultAddress;
import org.mallfoundry.test.StandaloneTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@StandaloneTest
public class JpaOrderRepositoryTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JpaOrderRepository repository;

    @Transactional
    @Rollback(false)
    @Test
    public void testAddShipment() {

        var selections = List.of(new DefaultOptionSelection().toBuilder().nameId("n1").valueId("v1").build());
        var order = new JpaOrder("o1");
        order.setStoreId("mi");
        order.setStoreName("mi name");
        order.setCustomerId("c1");
        order.setShippingAddress(new DefaultAddress());
        order.setSource(OrderSource.BROWSER);
        order.addItem(order.createItem("o1i1").toBuilder()
                .storeId("mi").productId("p1").variantId("p1v1")
                .price(BigDecimal.valueOf(10)).quantity(2)
                .name("name1").imageUrl("image url").optionSelections(selections).build());
        order.addItem(order.createItem("o1i2").toBuilder()
                .storeId("mi").productId("p1").variantId("p1v2")
                .price(BigDecimal.valueOf(10)).quantity(2)
                .name("name2").imageUrl("image url").optionSelections(selections).build());

        var s1 = order.createShipment("s1").toBuilder()
                .consignor("c1").consignorId("ci1").shippingAddress(new DefaultAddress())
                .build();
        s1.addItem(s1.createItem("s1i1").toBuilder().itemId("o1i1").quantity(1).build());
        s1.addItem(s1.createItem("s1i2").toBuilder().itemId("o1i2").quantity(1).build());
        order.addShipment(s1);
        var s2 = order.createShipment("s2").toBuilder()
                .consignor("c1").consignorId("ci1").shippingAddress(new DefaultAddress())
                .build();
        s2.addItem(s2.createItem("s2i1").toBuilder().itemId("o1i1").quantity(1).build());
        s2.addItem(s2.createItem("s2i2").toBuilder().itemId("o1i2").quantity(1).build());
        order.addShipment(s2);
        this.repository.save(order);
        /*var so1 = this.repository.findById("o1");*/
    }

    @Transactional
    @Test
    public void testFindShipmentEagerSQL() {
        var o1 = this.repository.getOne("o1");
        var shipments = o1.getShipments();
        logger.debug(shipments.toString());
    }
}
