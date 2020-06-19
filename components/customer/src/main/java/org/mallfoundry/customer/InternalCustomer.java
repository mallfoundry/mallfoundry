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

package org.mallfoundry.customer;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_customer")
public class InternalCustomer implements Customer {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "username_")
    private String username;

    @Column(name = "avatar_")
    private String avatar;

    @Column(name = "nickname_")
    private String nickname;

    @Column(name = "gender_")
    private Gender gender;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthday_")
    private Date birthday;

    @OneToMany(targetEntity = InternalCustomerAddress.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id_")
    @OrderBy("defaulted DESC , createdTime ASC")
    private List<CustomerAddress> addresses = new ArrayList<>();

    public InternalCustomer(String userId) {
        this.id = userId;
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

    @Override
    public Optional<CustomerAddress> getDefaultAddress() {
        return this.addresses.stream().filter(CustomerAddress::isDefaulted).findFirst();
    }

    @Override
    public Optional<CustomerAddress> getAddress(String addressId) {
        return this.addresses.stream().filter(address -> Objects.equals(address.getId(), addressId)).findFirst();
    }

    @Override
    public CustomerAddress createAddress(String id) {
        return new InternalCustomerAddress(id);
    }

    @Override
    public void addAddress(final CustomerAddress address) {
        this.addresses.remove(address);
        this.addresses.add(address);
        // defaulted
        if (address.isDefaulted()) {
            this.getDefaultAddress()
                    .ifPresent(defaultAddress -> {
                        if (!Objects.equals(defaultAddress, address)) {
                            defaultAddress.setDefaulted(false);
                        }
                    });
        }
    }

    @Override
    public void setAddress(CustomerAddress shippingAddress) {

    }

    @Override
    public void removeAddress(CustomerAddress address) {
        this.addresses.remove(address);
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
