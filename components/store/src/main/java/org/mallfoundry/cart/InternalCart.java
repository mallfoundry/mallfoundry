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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
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
    @OrderBy("addedTime ASC")
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
    public List<CartItem> getItems(Collection<String> itemIds) {
        return this.items.stream().filter(item -> itemIds.contains(item.getId())).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void adjustItemQuantity(String itemId, int quantityDelta) throws CartException {
        this.getItem(itemId).orElseThrow().adjustQuantity(quantityDelta);
    }

    @Override
    public void checkItem(String itemId) {
        this.checkItems(Set.of(itemId));
    }

    @Override
    public void uncheckItem(String itemId) {
        this.uncheckItems(Set.of(itemId));
    }

    @Override
    public void checkItems(Collection<String> itemIds) {
        itemIds.stream().map(this::getItem).map(Optional::orElseThrow).forEach(CartItem::check);
    }

    @Override
    public void uncheckItems(Collection<String> itemIds) {
        itemIds.stream().map(this::getItem).map(Optional::orElseThrow).forEach(CartItem::uncheck);
    }

    @Override
    public void checkAllItems() {
        this.items.forEach(CartItem::check);
    }

    @Override
    public void uncheckAllItems() {
        this.items.forEach(CartItem::uncheck);
    }

    @Override
    public CartItem createItem(String id) {
        return new InternalCartItem(id);
    }

    private void adjustItem(CartItem newItem, CartItem targetItem) {
        targetItem.adjustQuantity(newItem.getQuantity());

        if (Objects.nonNull(newItem.getName())) {
            targetItem.setName(newItem.getName());
        }

        if (Objects.nonNull(newItem.getImageUrl())) {
            targetItem.setImageUrl(newItem.getImageUrl());
        }

        if (Objects.nonNull(newItem.getPrice())) {
            targetItem.setPrice(newItem.getPrice());
        }

        if (newItem.isChecked()) {
            targetItem.check();
        }
    }

    private Optional<CartItem> findItem(String productId, String variantId) {
        return this.items.stream().filter(eItem ->
                Objects.equals(eItem.getProductId(), productId)
                        && Objects.equals(eItem.getVariantId(), variantId))
                .findFirst();
    }

    @Override
    public void addItem(CartItem newItem) {
        this.findItem(newItem.getProductId(), newItem.getVariantId())
                .ifPresentOrElse(eItem -> this.adjustItem(newItem, eItem), () -> this.items.add(newItem));
    }

    @Override
    public void addItems(Collection<CartItem> items) {
        items.forEach(this::addItem);
    }

    private void setItemSelf(CartItem newItem) {
        var item = this.getItem(newItem.getId()).orElseThrow(CartExceptions.itemNotExist());
        if (Objects.nonNull(newItem.getProductId()) && Objects.nonNull(newItem.getVariantId())) {
            if (!Objects.equals(item.getProductId(), newItem.getProductId())) {
                throw new CartException("You can only modify different variations of the same product");
            }
            item.setVariantId(Objects.requireNonNull(newItem.getVariantId()));
            item.setOptionSelections(Objects.requireNonNull(newItem.getOptionSelections()));
            item.setImageUrl(Objects.requireNonNull(newItem.getImageUrl()));
            item.setPrice(Objects.requireNonNull(newItem.getPrice()));
        }

        if (Objects.nonNull(newItem.getName())) {
            item.setName(newItem.getName());
        }

        if (newItem.getQuantity() != 0) {
            item.setQuantity(newItem.getQuantity());
        }
    }

    @Override
    public void setItem(CartItem newItem) {
        var item = this.getItem(newItem.getId()).orElseThrow();
        var itemId = item.getId();
        this.findItem(newItem.getProductId(), newItem.getVariantId())
                .filter(eItem -> !Objects.equals(itemId, eItem.getId()))
                .ifPresentOrElse(eItem -> {
                    this.adjustItem(newItem, eItem);
                    this.removeItem(item);
                }, () -> this.setItemSelf(newItem));
    }

    @Override
    public void removeItem(CartItem item) {
        this.items.remove(item);
    }

    @Override
    public void removeItems(Collection<CartItem> items) {
        items.forEach(this::removeItem);
    }
}
