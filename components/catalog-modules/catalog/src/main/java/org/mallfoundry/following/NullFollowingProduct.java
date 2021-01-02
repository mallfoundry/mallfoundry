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

import org.mallfoundry.catalog.option.Option;
import org.mallfoundry.catalog.product.ProductAttribute;
import org.mallfoundry.catalog.product.ProductOrigin;
import org.mallfoundry.catalog.product.ProductStatus;
import org.mallfoundry.catalog.product.ProductType;
import org.mallfoundry.catalog.product.ProductVariant;
import org.mallfoundry.inventory.InventoryStatus;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class NullFollowingProduct implements ImmutableFollowingProduct {

    private final ProductFollowing following;

    public NullFollowingProduct(ProductFollowing following) {
        this.following = following;
    }

    @Override
    public String getId() {
        return this.following.getProductId();
    }

    @Override
    public String getStoreId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getShortName() {
        return null;
    }

    @Override
    public ProductType getType() {
        return null;
    }

    @Override
    public ProductStatus getStatus() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public BigDecimal getPrice() {
        return null;
    }

    @Override
    public List<String> getCategories() {
        return null;
    }

    @Override
    public String getBrandId() {
        return null;
    }

    @Override
    public Set<String> getCollections() {
        return null;
    }

    @Override
    public Long getTotalSales() {
        return (long) 0;
    }

    @Override
    public Long getMonthlySales() {
        return (long) 0;
    }

    @Override
    public long getViewsCount() {
        return 0;
    }

    @Override
    public long getReviewsCount() {
        return 0;
    }

    @Override
    public List<String> getImageUrls() {
        return null;
    }

    @Override
    public List<String> getVideoUrls() {
        return null;
    }

    @Override
    public ProductOrigin getOrigin() {
        return null;
    }

    @Override
    public boolean isFreeShipping() {
        return false;
    }

    @Override
    public BigDecimal getFixedShippingCost() {
        return null;
    }

    @Override
    public String getShippingRateId() {
        return null;
    }

    @Override
    public int getInventoryQuantity() {
        return 0;
    }

    @Override
    public InventoryStatus getInventoryStatus() {
        return null;
    }

    @Override
    public List<ProductVariant> getVariants() {
        return Collections.emptyList();
    }

    @Override
    public List<Option> getOptions() {
        return Collections.emptyList();
    }

    @Override
    public Optional<Option> getOption(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<ProductAttribute> getAttribute(String namespace, String name) {
        return Optional.empty();
    }

    @Override
    public List<ProductAttribute> getAttributes() {
        return Collections.emptyList();
    }

    @Override
    public Date getCreatedTime() {
        return null;
    }

    @Override
    public Date getPublishedTime() {
        return null;
    }

    @Override
    public Date getFollowedTime() {
        return this.following.getFollowedTime();
    }
}
