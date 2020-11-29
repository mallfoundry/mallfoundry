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

package org.mallfoundry.rest.catalog.product;

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
import java.util.Set;

public class ProductResponse implements ImmutableProductResponse {

    private final List<String> fields;

    private final Product product;

    public ProductResponse(List<String> fields, Product product) {
        this.fields = fields;
        this.product = product;
    }

    @Override

    public String getId() {
        return this.product.getId();
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
        return null;
    }

    @Override
    public List<ProductOption> getOptions() {
        return null;
    }

    @Override
    public List<ProductAttribute> getAttributes() {
        return null;
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
    public BodyType getBodyType() {
        return null;
    }

    @Override
    public String getBody() {
        return null;
    }

    @Override
    public String getRawBody() {
        return null;
    }

    @Override
    public String getStoreId() {
        return null;
    }

    @Override
    public String getTenantId() {
        return null;
    }
}
