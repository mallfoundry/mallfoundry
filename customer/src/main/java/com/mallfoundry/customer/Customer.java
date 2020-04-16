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
import org.springframework.beans.BeanUtils;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
@Table(name = "customers")
public class Customer {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "id_"))
    private CustomerId id;

    @Column(name = "user_id_")
    private String userId;

    @Column(name = "nickname_")
    private String nickname;

    @Column(name = "gender_")
    private Gender gender;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "birthday_")
    private Date birthday;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id_")
    @OrderBy("addedTime ASC")
    private List<ShippingAddress> shippingAddresses = new ArrayList<>();

    @JsonIgnore
    @ElementCollection
    @CollectionTable(name = "customer_search_term", joinColumns = @JoinColumn(name = "customer_id_"))
    @OrderBy("time DESC")
    private List<SearchTerm> searchTerms = new ArrayList<>();

    @JsonIgnore
    public Optional<ShippingAddress> getDefaultShippingAddress() {
        return this.getShippingAddresses().stream().filter(ShippingAddress::isDefaulted).findFirst();
    }

    public Optional<ShippingAddress> getShippingAddress(Long id) {
        return this.getShippingAddresses().stream().filter(address -> Objects.equals(address.getId(), id)).findFirst();
    }

    public void addShippingAddress(final ShippingAddress address) {
        address.nowAddedTimeIfNull();
        this.getShippingAddress(address.getId())
                .ifPresentOrElse(
                        oldAddress -> BeanUtils.copyProperties(address, oldAddress, "id", "addedTime"),
                        () -> this.getShippingAddresses().add(address));
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

    public void removeShippingAddress(ShippingAddress address) {
        this.getShippingAddresses().remove(address);
    }

    public void addSearchTerm(String text) {
        this.searchTerms
                .stream()
                .filter(term -> Objects.equals(term.getText(), text))
                .findFirst()
                .ifPresentOrElse(SearchTerm::nowTime,
                        () -> this.searchTerms.add(new SearchTerm(text)));
    }

    public void removeSearchTerm(String text) {
        this.searchTerms.remove(new SearchTerm(text));
    }

    public void clearSearchTerms() {
        this.searchTerms.clear();
    }

}
