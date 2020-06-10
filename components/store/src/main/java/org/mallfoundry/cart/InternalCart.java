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
    public void checkItem(String itemId) {
        this.checkItems(List.of(itemId));
    }

    @Override
    public void uncheckItem(String itemId) {
        this.uncheckItems(List.of(itemId));
    }

    @Override
    public void checkItems(List<String> itemIds) {
        itemIds.stream().map(this::getItem).map(Optional::orElseThrow).forEach(CartItem::check);
    }

    @Override
    public void uncheckItems(List<String> itemIds) {
        itemIds.stream().map(this::getItem).map(Optional::orElseThrow).forEach(CartItem::uncheck);
    }

    @Override
    public CartItem createItem(String id) {
        return new InternalCartItem(id);
    }

    private void incrementItem(CartItem newItem, CartItem targetItem) {
        targetItem.incrementQuantity(newItem.getQuantity());
        targetItem.setName(newItem.getName());
        targetItem.setImageUrl(newItem.getImageUrl());
        targetItem.setOptionSelections(newItem.getOptionSelections());
        targetItem.setPrice(newItem.getPrice());
    }

    @Override
    public void addItem(CartItem newItem) {
        this.items.stream().filter(eItem ->
                Objects.equals(eItem.getProductId(), newItem.getProductId())
                        && Objects.equals(eItem.getVariantId(), newItem.getVariantId()))
                .findFirst()
                .ifPresentOrElse(eItem -> this.incrementItem(newItem, eItem), () -> this.items.add(newItem));
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
