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

package com.mallfoundry.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "id_"))
    private CustomerId id;

    @Column(name = "user_id_")
    private String userId;

    @Column(name = "nickname_")
    private String nickname;

//    @Embedded
//    private CustomerAvatar avatar;

    @Column(name = "gender_")
    private Gender gender;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "birthday_")
    private Date birthday;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id_")
    @OrderBy("addedTime ASC")
    private List<DeliveryAddress> deliveryAddresses = new ArrayList<>();

    @JsonIgnore
    private Optional<DeliveryAddress> getDefaultDeliveryAddress() {
        return this.getDeliveryAddresses().stream().filter(DeliveryAddress::isDefaulted).findFirst();
    }

    public void addDeliveryAddress(DeliveryAddress address) {
        this.getDeliveryAddresses().remove(address);
        this.getDefaultDeliveryAddress()
                .ifPresent(defaultAddress -> {
                    if (Objects.equals(defaultAddress, address)) {
                        defaultAddress.setDefaulted(false);
                    }
                });
        address.nowAddedTime();
        this.getDeliveryAddresses().add(address);
    }

    public void removeDeliveryAddress(DeliveryAddress address) {
        this.getDeliveryAddresses().remove(address);
    }
}
