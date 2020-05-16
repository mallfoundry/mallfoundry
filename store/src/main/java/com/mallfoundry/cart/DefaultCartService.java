package com.mallfoundry.cart;

import com.mallfoundry.keygen.PrimaryKeyHolder;
import com.mallfoundry.store.product.ProductService;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DefaultCartService implements CartService {

    static final String CART_ID_VALUE_NAME = "cart.id";

    static final String CART_ITEM_ID_VALUE_NAME = "cart.item.id";

    private final ProductService productService;

    private final CartTokenService cartTokenService;

    private final CartRepository cartRepository;

    public DefaultCartService(ProductService productService,
                              CartTokenService cartTokenService,
                              CartRepository cartRepository) {
        this.productService = productService;
        this.cartTokenService = cartTokenService;
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart createCart() {
        return new InternalCart(PrimaryKeyHolder.next(CART_ID_VALUE_NAME));
    }

    @Override
    public CartItem createCartItem() {
        return new InternalCartItem(PrimaryKeyHolder.next(CART_ITEM_ID_VALUE_NAME));
    }

    @Override
    public Optional<Cart> getCart(String token) {
        var cartId = this.cartTokenService.getCartId(token);
        return CastUtils.cast(this.cartRepository.findById(cartId));
    }

    @Transactional
    @Override
    public void addCartItem(String token, CartItem item) {
        this.getCart(token).orElseThrow().addItem(item);
    }

    @Transactional
    @Override
    public void saveCartItem(String token, CartItem newItem) {
        this.getCart(token).orElseThrow().getItem(newItem.getId()).orElseThrow();
//        this.cartRepository.
    }

    @Transactional
    @Override
    public void removeCartItem(String token, String itemId) {
        var cart = this.getCart(token).orElseThrow();
        cart.removeItem(cart.getItem(itemId).orElseThrow());
    }
}
