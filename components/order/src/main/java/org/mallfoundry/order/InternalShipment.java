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
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.shipping.Address;
import org.mallfoundry.shipping.repository.jpa.convert.AddressConverter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_shipments")
public class InternalShipment implements Shipment {

    @Id
    @Getter
    @Column(name = "id_")
    private String id;

    private String consignorId;

    private String consignor;

    @JsonProperty("shipping_address")
    @Convert(converter = AddressConverter.class)
    @Column(name = "shipping_address_")
    private Address shippingAddress;

    @JsonProperty("shipping_provider")
    @Column(name = "shipping_provider_")
    private String shippingProvider;

    @JsonProperty("shipping_method")
    @Column(name = "shipping_method_")
    private String shippingMethod;

    @JsonProperty("tracking_number")
    @Column(name = "tracking_number_")
    private String trackingNumber;

    @JsonProperty("order_id")
    @Column(name = "order_id_")
    private String orderId;

    @OneToMany(targetEntity = InternalOrderItem.class)
    @JoinTable(name = "order_shipments_items",
            joinColumns = @JoinColumn(name = "shipment_id_"),
            inverseJoinColumns = @JoinColumn(name = "order_item_id_"))
    private List<OrderItem> items = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("shipped_time")
    @Column(name = "shipped_time_")
    private Date shippedTime;

    public void setItems(List<OrderItem> items) {
        this.items = items.stream().map(InternalOrderItem::of).collect(Collectors.toList());
    }

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
