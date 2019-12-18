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

package com.mallfoundry.customer.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customer_cart")
public class Cart implements Serializable {

    @Id
    @Column(name = "id_")
    private String id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id_")
    private List<CartItem> items = new ArrayList<>();

    public Cart(String id) {
        this.setId(id);
    }

    public void addItem(CartItem item) {
        item.setCart(this);
        this.getItems().add(item);
    }

    public void removeItem(CartItem item) {
        item.setCart(this);
        this.getItems().remove(item);
    }

    public void removeItems(List<CartItem> items) {
        this.getItems().removeAll(items);
    }

    public Optional<CartItem> getItem(String variantId) {
        return this.getItems()
                .stream()
                .filter(item -> Objects.equals(item.getVariantId(), variantId))
                .findAny();
    }

    public List<CartItem> getItems(List<String> variantIds) {
        return this.getItems()
                .stream()
                .filter(item -> variantIds.contains(item.getVariantId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
