package com.mallfoundry.cart;

import com.mallfoundry.keygen.PrimaryKeyHolder;
import com.mallfoundry.security.SecurityUserHolder;
import com.mallfoundry.store.product.ProductService;
import lombok.Setter;
import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;


// mall.cart.scope=global,store
//
@Service
public class DefaultCartService implements CartService {

    static final String CART_ID_VALUE_NAME = "cart.id";

    static final String CART_ITEM_ID_VALUE_NAME = "cart.item.id";

    private final ProductService productService;

    private final CartRepository cartRepository;

    @Setter
    private CartScope cartScope = CartScope.GLOBAL;

    public DefaultCartService(ProductService productService,
                              CartRepository cartRepository) {
        this.productService = productService;
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart createCart() {
        return new InternalCart(PrimaryKeyHolder.next(CART_ID_VALUE_NAME));
    }

    @Override
    public CartItem createCartItem(String productId, String variantId, int quantity) {
        var product = this.productService.getProduct(productId).orElseThrow();
        var variant = product.getVariant(variantId).orElseThrow();
        return new InternalCartItem(PrimaryKeyHolder.next(CART_ITEM_ID_VALUE_NAME), productId, variantId)
                .toBuilder()
                .quantity(quantity)
                .imageUrl(variant.getFirstImageUrl())
                .name(product.getName())
                .optionValues(variant.getOptionValues())
                .build();
    }

    @Override
    public CartScope getCartScope() {
        return this.cartScope;
    }

    @Override
    public Optional<Cart> getCart(String token) {

        if (this.getCartScope().equals(CartScope.GLOBAL)) {

        }

        var customerId = SecurityUserHolder.getUserId();


        return CastUtils.cast(this.cartRepository.findById(token));
    }

    @Override
    public void addCartItem(String token, CartItem item) {

    }





}
