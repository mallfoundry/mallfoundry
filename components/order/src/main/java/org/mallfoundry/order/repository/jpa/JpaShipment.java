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

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.order.Shipment;
import org.mallfoundry.order.ShipmentItem;
import org.mallfoundry.order.ShipmentSupport;
import org.mallfoundry.order.repository.jpa.convert.ShipmentItemListConverter;
import org.mallfoundry.shipping.Address;
import org.mallfoundry.shipping.CarrierCode;
import org.mallfoundry.shipping.repository.jpa.convert.AddressConverter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_order_shipment")
public class JpaShipment extends ShipmentSupport {

    @NotBlank
    @Id
    @Column(name = "id_")
    private String id;

    @NotBlank
    @Column(name = "consignor_id_")
    private String consignorId;

    @NotBlank
    @Column(name = "consignor_")
    private String consignor;

    @NotNull
    @Convert(converter = AddressConverter.class)
    @Column(name = "shipping_address_")
    private Address shippingAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_provider_")
    private CarrierCode shippingProvider;

    @Column(name = "tracking_carrier_")
    private String trackingCarrier;

    @Column(name = "shipping_method_")
    private String shippingMethod;

    @Column(name = "tracking_number_")
    private String trackingNumber;

    @Valid
    @NotEmpty
    @Convert(converter = ShipmentItemListConverter.class)
    @Column(name = "items_", length = 1024 * 2)
    private List<ShipmentItem> items = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "shipped_time_")
    private Date shippedTime;

    public JpaShipment(String id) {
        this.setId(id);
        this.setShippedTime(new Date());
    }

    public static JpaShipment of(Shipment shipment) {
        if (shipment instanceof JpaShipment) {
            return (JpaShipment) shipment;
        }
        var target = new JpaShipment();
        BeanUtils.copyProperties(shipment, target);
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JpaShipment that = (JpaShipment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
