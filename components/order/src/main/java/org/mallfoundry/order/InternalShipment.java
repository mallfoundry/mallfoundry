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
