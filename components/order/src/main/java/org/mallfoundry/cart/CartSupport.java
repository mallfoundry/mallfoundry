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

package org.mallfoundry.cart;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class CartSupport implements Cart {

    @Override
    public CartAdjustment createAdjustment(String itemId) {
        return new DefaultCartAdjustment(itemId, 0);
    }

    @Override
    public void addItem(CartItem newItem) {
        this.findItem(newItem.getProductId(), newItem.getVariantId())
                .ifPresentOrElse(eItem -> this.adjustItem(newItem, eItem), () -> this.getItems().add(newItem));
    }

    @Override
    public void addItems(Collection<CartItem> items) {
        items.forEach(this::addItem);
    }

    @Override
    public Optional<CartItem> getItem(String itemId) {
        return this.getItems().stream().filter(item -> Objects.equals(item.getId(), itemId)).findFirst();
    }

    public Optional<CartItem> getItem(String productId, String variantId) {
        return this.getItems().stream()
                .filter(item -> Objects.equals(item.getProductId(), productId) && Objects.equals(item.getVariantId(), variantId))
                .findFirst();
    }

    private CartItem requiredItem(String itemId) {
        return this.getItem(itemId).orElseThrow();
    }

    private CartItem requiredItem(String productId, String variantId) {
        return this.getItem(productId, variantId).orElseThrow();
    }

    private CartItem requiredItem(CartAdjustment adjustment) {
        return Objects.nonNull(adjustment.getItemId())
                ? this.requiredItem(adjustment.getItemId())
                : this.requiredItem(adjustment.getProductId(), adjustment.getVariantId());
    }

    @Override
    public List<CartItem> getItems(Collection<String> itemIds) {
        return this.getItems().stream()
                .filter(item -> itemIds.contains(item.getId()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void adjustItem(CartAdjustment adjustment) throws CartException {
        var item = this.requiredItem(adjustment);
        item.adjustQuantity(adjustment.getQuantityDelta());
        if (item.getQuantity() == 0) {
            this.removeItem(item);
        }
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
        itemIds.stream().map(this::requiredItem).forEach(CartItem::check);
    }

    @Override
    public void uncheckItems(Collection<String> itemIds) {
        itemIds.stream().map(this::requiredItem).forEach(CartItem::uncheck);
    }

    @Override
    public void checkAllItems() {
        this.getItems().forEach(CartItem::check);
    }

    @Override
    public void uncheckAllItems() {
        this.getItems().forEach(CartItem::uncheck);
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
        return this.getItems().stream().filter(eItem ->
                Objects.equals(eItem.getProductId(), productId)
                        && Objects.equals(eItem.getVariantId(), variantId))
                .findFirst();
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
    public void updateItem(CartItem newItem) {
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
        this.getItems().remove(item);
    }

    @Override
    public void removeItems(Collection<CartItem> items) {
        items.forEach(this::removeItem);
    }
}
