package org.mallfoundry.cart;

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
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_cart")
public class InternalCart implements Cart {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "customer_id_")
    private String customerId;

    @OneToMany(targetEntity = InternalCartItem.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id_")
    private List<CartItem> items = new ArrayList<>();

    public InternalCart(String id) {
        this.id = id;
    }

    public static InternalCart of(Cart cart) {
        if (cart instanceof InternalCart) {
            return (InternalCart) cart;
        }
        var target = new InternalCart();
        BeanUtils.copyProperties(cart, target);
        return target;
    }

    @Override
    public Optional<CartItem> getItem(String itemId) {
        return this.items.stream().filter(item -> Objects.equals(item.getId(), itemId)).findFirst();
    }

    @Override
    public List<CartItem> getItems(List<String> itemIds) {
        return this.items.stream().filter(item -> itemIds.contains(item.getId())).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public CartItem createItem(String id) {
        return new InternalCartItem(id);
    }

    @Override
    public void addItem(CartItem newItem) {
        this.items.add(newItem);
    }

    @Override
    public void setItem(CartItem newItem) {
        this.items.remove(newItem);
    }

    @Override
    public void removeItem(CartItem item) {
        this.items.remove(item);
    }

    @Override
    public void removeItems(List<CartItem> items) {
        items.forEach(this::removeItem);
    }
}
