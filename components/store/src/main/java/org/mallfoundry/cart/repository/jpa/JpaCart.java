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

package org.mallfoundry.cart.repository.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.cart.Cart;
import org.mallfoundry.cart.CartItem;
import org.mallfoundry.cart.CartSupport;
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
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_cart")
public class JpaCart extends CartSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "customer_id_")
    private String customerId;

    @OneToMany(targetEntity = JpaCartItem.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id_")
    @OrderBy("addedTime ASC")
    private List<CartItem> items = new ArrayList<>();

    public JpaCart(String id) {
        this.id = id;
    }

    public static JpaCart of(Cart cart) {
        if (cart instanceof JpaCart) {
            return (JpaCart) cart;
        }
        var target = new JpaCart();
        BeanUtils.copyProperties(cart, target);
        return target;
    }

    @Override
    public CartItem createItem(String id) {
        return new JpaCartItem(id);
    }
}
