package com.mallfoundry.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class InternalCart implements Cart {

    @Id
    @Column(name = "id_")
    private String id;

    @OneToMany(targetEntity = InternalCartItem.class)
    @JoinColumn(name = "cart_id_")
    private List<CartItem> items = new ArrayList<>();

    public InternalCart(String id) {
        this.id = id;
    }

    @Override
    public Optional<CartItem> getItem(String itemId) {
        return this.items.stream().filter(item -> Objects.equals(item.getId(), itemId)).findFirst();
    }

    @Override
    public void addItem(CartItem newItem) {
        this.items.add(newItem);
    }

    @Override
    public void removeItem(CartItem item) {
        this.items.remove(item);
    }
}
