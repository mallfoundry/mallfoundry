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

import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.catalog.product.ProductService;
import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.mallfoundry.security.SubjectHolder;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DefaultCartService implements CartService {

    static final String CART_ITEM_ID_VALUE_NAME = "cart.item.id";

    private final ProductService productService;

    private final CartRepository cartRepository;

    public DefaultCartService(ProductService productService, CartRepository cartRepository) {
        this.productService = productService;
        this.cartRepository = cartRepository;
    }

    private InternalCart createEmptyCart(String id) {
        var cart = (InternalCart) new InternalCart(id).toBuilder().customerId(SubjectHolder.getUserId()).build();
        return this.cartRepository.save(cart);
    }

    @Override
    public Cart createCart(String id) {
        return new InternalCart(id).toBuilder().customerId(SubjectHolder.getUserId()).build();
    }

//    @Override
//    public CartItem createCartItem() {
//        return new InternalCartItem(PrimaryKeyHolder.next(CART_ITEM_ID_VALUE_NAME));
//    }

    @Override
    public Cart createCart(Cart cart) {
        return this.cartRepository.save(InternalCart.of(cart));
    }

    @Override
    public void deleteCart(String id) {
        var cart = this.cartRepository.findById(id).orElseThrow();
        this.cartRepository.delete(cart);
    }

    private Optional<InternalCart> getInternalCart(String id) {
        return this.cartRepository.findById(id);
    }

    @Transactional
    @Override
    public Optional<Cart> getCart(String id) {
        return CastUtils.cast(this.getInternalCart(id));
    }

    private InternalCartItem setCartItem(CartItem newItem) {
        var item = InternalCartItem.of(newItem);
        var product = this.productService.getProduct(item.getProductId()).orElseThrow();
        var variant = product.getVariant(item.getVariantId()).orElseThrow();
        item.setOptionSelections(variant.getOptionSelections());
        item.setStoreId(product.getStoreId());
        item.setPrice(variant.getPrice());

        if (StringUtils.isBlank(item.getName())) {
            item.setName(product.getName());
        }

        if (StringUtils.isBlank(item.getImageUrl())) {
            item.setImageUrl(CollectionUtils.firstElement(variant.getImageUrls()));
        }
        return item;
    }

    @Transactional
    @Override
    public CartItem addCartItem(String id, CartItem newItem) {
        var mapItem = Stream.of(newItem)
                .map(InternalCartItem::of)
                .peek(item -> item.setId(PrimaryKeyHolder.next(CART_ITEM_ID_VALUE_NAME)))
                .map(this::setCartItem)
                .findFirst()
                .orElseThrow();
        this.getCart(id).orElseGet(() -> this.createEmptyCart(id)).addItem(mapItem);
        return mapItem;
    }

    @Transactional
    @Override
    public List<CartItem> addCartItems(String id, Collection<CartItem> items) {
        List<CartItem> itemList = items.stream().map(InternalCartItem::of)
                .peek(item -> item.setId(PrimaryKeyHolder.next(CART_ITEM_ID_VALUE_NAME)))
                .map(this::setCartItem)
                .collect(Collectors.toList());
        this.getCart(id).orElseGet(() -> this.createEmptyCart(id))
                .addItems(itemList);
        return itemList;
    }

    @Transactional
    @Override
    public void updateCartItem(String id, CartItem newItem) {
        var cart = this.getInternalCart(id).orElseThrow();
        if (Objects.nonNull(newItem.getProductId())
                && Objects.nonNull(newItem.getVariantId())) {
            this.setCartItem(newItem);
        }
        cart.setItem(newItem);
    }

    @Transactional
    @Override
    public void removeCartItem(String id, String itemId) {
        var cart = this.getCart(id).orElseThrow();
        cart.removeItem(cart.getItem(itemId).orElseThrow());
    }

    @Transactional
    @Override
    public void removeCartItems(String id, Collection<String> itemIds) {
        var cart = this.getCart(id).orElseThrow();
        cart.removeItems(cart.getItems(itemIds));
    }

    @Transactional
    @Override
    public void adjustCartItemQuantity(String id, String itemId, int quantityDelta) {
        var cart = this.getCart(id).orElseThrow();
        cart.adjustItemQuantity(itemId, quantityDelta);
    }

    @Transactional
    @Override
    public void checkCartItem(String id, String itemId) {
        var cart = this.getCart(id).orElseThrow();
        cart.checkItem(itemId);
    }

    @Transactional
    @Override
    public void uncheckCartItem(String id, String itemId) {
        var cart = this.getCart(id).orElseThrow();
        cart.uncheckItem(itemId);
    }

    @Transactional
    @Override
    public void checkCartItems(String id, Collection<String> itemIds) {
        var cart = this.getCart(id).orElseThrow();
        cart.checkItems(itemIds);
    }

    @Transactional
    @Override
    public void uncheckCartItems(String id, Collection<String> itemIds) {
        var cart = this.getCart(id).orElseThrow();
        cart.uncheckItems(itemIds);
    }

    @Transactional
    @Override
    public void checkAllCartItems(String id) {
        var cart = this.getCart(id).orElseThrow();
        cart.checkAllItems();
    }

    @Transactional
    @Override
    public void uncheckAllCartItems(String id) {
        var cart = this.getCart(id).orElseThrow();
        cart.uncheckAllItems();
    }
}
