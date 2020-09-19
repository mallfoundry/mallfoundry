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

import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductAttribute;
import org.mallfoundry.catalog.product.ProductOption;
import org.mallfoundry.catalog.product.ProductOrigin;
import org.mallfoundry.catalog.product.ProductStatus;
import org.mallfoundry.catalog.product.ProductType;
import org.mallfoundry.catalog.product.ProductVariant;
import org.mallfoundry.inventory.InventoryStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DelegatingImmutableFollowingProduct implements ImmutableFollowingProduct {

    private final Product product;

    private final ProductFollowing following;

    public DelegatingImmutableFollowingProduct(Product product, ProductFollowing following) {
        this.product = product;
        this.following = following;
    }

    @Override
    public String getId() {
        return this.product.getId();
    }

    @Override
    public String getTenantId() {
        return this.product.getTenantId();
    }

    @Override
    public String getStoreId() {
        return this.product.getStoreId();
    }

    @Override
    public String getName() {
        return this.product.getName();
    }

    @Override
    public ProductType getType() {
        return this.product.getType();
    }

    @Override
    public ProductStatus getStatus() {
        return this.product.getStatus();
    }

    @Override
    public String getDescription() {
        return this.product.getDescription();
    }

    @Override
    public BigDecimal getPrice() {
        return this.product.getPrice();
    }

    @Override
    public List<String> getCategories() {
        return this.product.getCategories();
    }

    @Override
    public String getBrandId() {
        return this.product.getBrandId();
    }

    @Override
    public Set<String> getCollections() {
        return this.product.getCollections();
    }

    @Override
    public Long getTotalSales() {
        return this.product.getTotalSales();
    }

    @Override
    public Long getMonthlySales() {
        return this.product.getMonthlySales();
    }

    @Override
    public long getViewsCount() {
        return this.product.getViewsCount();
    }

    @Override
    public long getReviewsCount() {
        return this.product.getReviewsCount();
    }

    @Override
    public List<String> getImageUrls() {
        return this.product.getImageUrls();
    }

    @Override
    public List<String> getVideoUrls() {
        return this.product.getVideoUrls();
    }

    @Override
    public ProductOrigin getOrigin() {
        return this.product.getOrigin();
    }

    @Override
    public boolean isFreeShipping() {
        return this.product.isFreeShipping();
    }

    @Override
    public BigDecimal getFixedShippingCost() {
        return this.product.getFixedShippingCost();
    }

    @Override
    public String getShippingRateId() {
        return this.product.getShippingRateId();
    }

    @Override
    public int getInventoryQuantity() {
        return this.product.getInventoryQuantity();
    }

    @Override
    public InventoryStatus getInventoryStatus() {
        return this.product.getInventoryStatus();
    }

    @Override
    public List<ProductVariant> getVariants() {
        return this.product.getVariants();
    }

    @Override
    public ProductVariant getVariant(String variantId) {
        return null;
    }

    @Override
    public Optional<ProductVariant> findVariant(String variantId) {
        return Optional.empty();
    }

    @Override
    public List<ProductOption> getOptions() {
        return this.product.getOptions();
    }

    @Override
    public Optional<ProductOption> getOption(String name) {
        return this.product.getOption(name);
    }

    @Override
    public Optional<ProductAttribute> getAttribute(String namespace, String name) {
        return this.product.getAttribute(namespace, name);
    }

    @Override
    public List<ProductAttribute> getAttributes() {
        return this.product.getAttributes();
    }

    @Override
    public Date getCreatedTime() {
        return this.product.getCreatedTime();
    }

    @Override
    public Date getPublishedTime() {
        return this.product.getPublishedTime();
    }

    @Override
    public Date getFollowedTime() {
        return this.following.getFollowedTime();
    }
}
