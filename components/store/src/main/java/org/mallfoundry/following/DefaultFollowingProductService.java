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

package org.mallfoundry.following;

import org.apache.commons.collections4.ListUtils;
import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductService;
import org.mallfoundry.data.SliceList;
import org.mallfoundry.identity.UserService;
import org.mallfoundry.processor.Processors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DefaultFollowingProductService implements FollowingProductService, FollowingProductProcessorInvoker {

    private List<FollowingProductProcessor> processors = Collections.emptyList();

    private final UserService userService;

    private final ProductService productService;

    private final ProductFollowingRepository productFollowingRepository;

    public DefaultFollowingProductService(UserService userService,
                                          ProductService productService,
                                          ProductFollowingRepository productFollowingRepository) {
        this.userService = userService;
        this.productService = productService;
        this.productFollowingRepository = productFollowingRepository;
    }

    public void setProductProcessors(List<FollowingProductProcessor> processors) {
        this.processors = ListUtils.emptyIfNull(processors);
    }

    @Override
    public FollowingProductQuery createFollowProductQuery() {
        return new DefaultFollowingProductQuery();
    }

    private FollowingProduct requiredFollowingProduct(ProductFollowing following) {
        return this.productService.getProduct(following.getProductId())
                .<FollowingProduct>map(product -> new DelegatingImmutableFollowingProduct(product, following))
                .orElseGet(() -> new NullFollowingProduct(following));
    }

    private Follower getFollower(String followerId) {
        var userId = this.userService.createUserId(followerId);
        var user = this.userService.getUser(userId);
        return new DelegatingImmutableFollower(user);
    }

    @Transactional
    @Override
    public void followProduct(String followerId, String productId) {
        var following = this.productFollowingRepository.create(followerId, productId);
        var followingProduct = this.requiredFollowingProduct(following);
        followingProduct = this.invokePreProcessBeforeFollowProduct(this.getFollower(followerId), followingProduct);
        following.setProductId(followingProduct.getId());
        if (this.productFollowingRepository.exists(following)) {
            throw new FollowingException("The follower has followed to this product");
        }
        this.productFollowingRepository.save(following);
    }

    @Transactional
    @Override
    public void unfollowProduct(String followerId, String productId) {
        var following = this.productFollowingRepository.create(followerId, productId);
        var followingProduct = this.requiredFollowingProduct(following);
        followingProduct = this.invokePreProcessBeforeUnfollowProduct(this.getFollower(followerId), followingProduct);
        following.setProductId(followingProduct.getId());
        this.productFollowingRepository.delete(following);
    }

    @Override
    public boolean checkFollowingProduct(String followerId, String productId) {
        var following = this.productFollowingRepository.create(followerId, productId);
        var followingProduct = this.requiredFollowingProduct(following);
        followingProduct = this.invokePreProcessBeforeCheckFollowingProduct(this.getFollower(followerId), followingProduct);
        following.setProductId(followingProduct.getId());
        return this.productFollowingRepository.exists(following);
    }

    private FollowingProduct getFollowProduct(List<Product> products, ProductFollowing following) {
        return products.stream()
                .filter(product -> Objects.equals(product.getId(), following.getProductId()))
                .findFirst()
                .<FollowingProduct>map(aProduct -> new DelegatingImmutableFollowingProduct(aProduct, following))
                .orElseGet(() -> new NullFollowingProduct(following));
    }

    @Override
    public SliceList<FollowingProduct> getFollowingProducts(FollowingProductQuery query) {
        var sliceFollowings = Function.<SliceList<ProductFollowing>>identity()
                .compose(this.productFollowingRepository::findAll)
                .compose(this::invokePreProcessBeforeGetFollowingProducts)
                .apply(query);
        var productIds = ListUtils.emptyIfNull(sliceFollowings.getElements()).stream()
                .map(ProductFollowing::getProductId)
                .collect(Collectors.toUnmodifiableList());
        if (CollectionUtils.isEmpty(productIds)) {
            return sliceFollowings.elements(Collections.emptyList());
        }
        var products = this.productService.getProducts(productIds);
        var followProducts = sliceFollowings.getElements().stream()
                .map(following -> this.getFollowProduct(products, following))
                .collect(Collectors.toUnmodifiableList());
        return sliceFollowings.elements(followProducts);
    }

    @Override
    public long countFollowingProducts(FollowingProductQuery query) {
        return Function.<Long>identity()
                .compose(this.productFollowingRepository::count)
                .compose(this::invokePreProcessBeforeCountFollowingProducts)
                .apply(query);
    }

    @Override
    public FollowingProduct invokePreProcessBeforeFollowProduct(Follower follower, FollowingProduct product) {
        return Processors.stream(this.processors)
                .<FollowingProduct>map((processor, identity) -> processor.preProcessBeforeFollowProduct(follower, identity))
                .apply(product);
    }

    @Override
    public FollowingProduct invokePreProcessBeforeUnfollowProduct(Follower follower, FollowingProduct product) {
        return Processors.stream(this.processors)
                .<FollowingProduct>map((processor, identity) -> processor.preProcessBeforeUnfollowProduct(follower, identity))
                .apply(product);
    }

    @Override
    public FollowingProduct invokePreProcessBeforeCheckFollowingProduct(Follower follower, FollowingProduct product) {
        return Processors.stream(this.processors)
                .<FollowingProduct>map((processor, identity) -> processor.preProcessBeforeCheckFollowingProduct(follower, identity))
                .apply(product);
    }

    @Override
    public FollowingProductQuery invokePreProcessBeforeGetFollowingProducts(FollowingProductQuery query) {
        return Processors.stream(this.processors)
                .map(FollowingProductProcessor::preProcessBeforeGetFollowingProducts)
                .apply(query);
    }

    @Override
    public FollowingProductQuery invokePreProcessBeforeCountFollowingProducts(FollowingProductQuery query) {
        return Processors.stream(this.processors)
                .map(FollowingProductProcessor::preProcessBeforeCountFollowingProducts)
                .apply(query);
    }
}
