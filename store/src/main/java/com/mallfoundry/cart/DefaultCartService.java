package com.mallfoundry.cart;

import com.mallfoundry.keygen.PrimaryKeyHolder;
import com.mallfoundry.security.SecurityUserHolder;
import com.mallfoundry.catalog.product.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        var cart = (InternalCart) new InternalCart(id).toBuilder().customerId(SecurityUserHolder.getUserId()).build();
        return this.cartRepository.save(cart);
    }

    @Override
    public Cart createCart(String id) {
        return new InternalCart(id).toBuilder().customerId(SecurityUserHolder.getUserId()).build();
    }

    @Override
    public CartItem createCartItem() {
        return new InternalCartItem(PrimaryKeyHolder.next(CART_ITEM_ID_VALUE_NAME));
    }

    @Override
    public Cart saveCart(Cart cart) {
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
        if (StringUtils.isBlank(item.getName())) {
            item.setName(product.getName());
        }

        if (StringUtils.isBlank(item.getImageUrl())) {
            item.setImageUrl(variant.getFirstImageUrl());
        }

        return item;
    }

    @Transactional
    @Override
    public void addCartItem(String id, CartItem newItem) {
        this.getCart(id).orElseGet(() -> this.createEmptyCart(id)).addItem(this.setCartItem(newItem));
    }

    @Transactional
    @Override
    public void setCartItem(String id, CartItem newItem) {
        var cart = this.getInternalCart(id).orElseThrow();
        cart.addItem(setCartItem(newItem));
    }

    @Transactional
    @Override
    public void removeCartItem(String id, String itemId) {
        var cart = this.getCart(id).orElseThrow();
        cart.removeItem(cart.getItem(itemId).orElseThrow());
    }

    @Override
    public void removeCartItems(String id, List<String> itemIds) {
        var cart = this.getCart(id).orElseThrow();
        cart.removeItems(cart.getItems(itemIds));
    }
}
