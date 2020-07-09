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

package org.mallfoundry.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_order_shipment")
public class InternalShipment implements Shipment {

    @Id
    @Getter
    @Column(name = "id_")
    private String id;

    @Column(name = "consignor_id_")
    private String consignorId;

    @Column(name = "consignor_")
    private String consignor;

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

    @Convert(converter = ShipmentItemListConverter.class)
    @Column(name = "items_", length = 1024 * 2)
    private List<ShipmentItem> items = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "shipped_time_")
    private Date shippedTime;

    public InternalShipment(String id) {
        this.id = id;
        this.setShippedTime(new Date());
    }

    public static InternalShipment of(Shipment shipment) {
        if (shipment instanceof InternalShipment) {
            return (InternalShipment) shipment;
        }
        var target = new InternalShipment();
        BeanUtils.copyProperties(shipment, target);
        return target;
    }

    @Transient
    @Override
    public List<String> getImageUrls() {
        return this.items.stream().map(ShipmentItem::getImageUrl).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ShipmentItem createItem(String id) {
        return new InternalShipmentItem();
    }

    @Override
    public Optional<ShipmentItem> getItem(String id) {
        return this.getItems().stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    @Override
    public void addItem(ShipmentItem item) {
        this.getItems().add(item);
    }

    @Override
    public void removeItem(ShipmentItem item) {
        this.getItems().remove(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InternalShipment that = (InternalShipment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
