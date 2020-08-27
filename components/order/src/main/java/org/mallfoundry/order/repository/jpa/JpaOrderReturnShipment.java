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

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.order.OrderReturnShipment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter

@Entity
@Table(name = "mf_order_return_shipment")
public class JpaOrderReturnShipment extends JpaOrderShipmentBase implements OrderReturnShipment {

    @Column(name = "refund_id_")
    private String refundId;

    @Column(name = "exchange_id_")
    private String exchangeId;

    public JpaOrderReturnShipment() {
        super(null);
    }

    public JpaOrderReturnShipment(String id) {
        super(id);
    }
}
