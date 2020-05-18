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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "customers")
public class InternalCustomer implements Customer {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "user_id_")
    private String userId;

    @Column(name = "nickname_")
    private String nickname;

    @Column(name = "gender_")
    private Gender gender;

    @Column(name = "birthday_")
    private Date birthday;

    @OneToMany(targetEntity = InternalShippingAddress.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id_")
    @OrderBy("createdTime ASC")
    private List<ShippingAddress> shippingAddresses = new ArrayList<>();

    public InternalCustomer(String userId) {
        this.id = userId;
        this.userId = userId;
        this.gender = Gender.UNKNOWN;
        this.birthday = new Date();
    }

    public static InternalCustomer of(Customer customer) {
        if (customer instanceof InternalCustomer) {
            return (InternalCustomer) customer;
        }
        var target = new InternalCustomer();
        BeanUtils.copyProperties(customer, target);
        return target;
    }


    //    @JsonIgnore
//    @ElementCollection
//    @CollectionTable(name = "customer_search_term", joinColumns = @JoinColumn(name = "customer_id_"))
//    @OrderBy("time DESC")
//    private List<SearchTerm> searchTerms = new ArrayList<>();

    @Override
    public Optional<ShippingAddress> getDefaultShippingAddress() {
        return this.getShippingAddresses().stream().filter(ShippingAddress::isDefaulted).findFirst();
    }

    @Override
    public Optional<ShippingAddress> getShippingAddress(String addressId) {
        return this.getShippingAddresses().stream().filter(address -> Objects.equals(address.getId(), addressId)).findFirst();
    }

    @Override
    public void addShippingAddress(final ShippingAddress address) {
        this.shippingAddresses.remove(address);
        this.shippingAddresses.add(address);
        // defaulted
        if (address.isDefaulted()) {
            this.getDefaultShippingAddress()
                    .ifPresent(defaultAddress -> {
                        if (!Objects.equals(defaultAddress, address)) {
                            defaultAddress.setDefaulted(false);
                        }
                    });
        }
    }

    @Override
    public void removeShippingAddress(ShippingAddress address) {
        this.getShippingAddresses().remove(address);
    }

//    public void addSearchTerm(String text) {
//        this.searchTerms
//                .stream()
//                .filter(term -> Objects.equals(term.getText(), text))
//                .findFirst()
//                .ifPresentOrElse(SearchTerm::nowTime,
//                        () -> this.searchTerms.add(new SearchTerm(text)));
//    }
//
//    public void removeSearchTerm(String text) {
//        this.searchTerms.remove(new SearchTerm(text));
//    }
//
//    public void clearSearchTerms() {
//        this.searchTerms.clear();
//    }

}
