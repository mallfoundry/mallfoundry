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

package org.mallfoundry.catalog.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.ListUtils;
import org.mallfoundry.catalog.DefaultOptionSelection;
import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.catalog.OptionSelections;
import org.mallfoundry.catalog.option.Option;
import org.mallfoundry.inventory.InventoryStatus;
import org.mallfoundry.util.Positions;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
@NoArgsConstructor
public abstract class ProductSupport implements MutableProduct {

    public ProductSupport(String id) {
        this.setId(id);
    }

    @Override
    public void freeShipping() {
        this.setFreeShipping(true);
        this.setFixedShippingCost(null);
        this.setShippingRateId(null);
    }

    @Override
    public void setFixedShippingCost(BigDecimal fixedShippingCost) throws ProductException {
        if (Objects.nonNull(fixedShippingCost)) {
            this.setShippingRateId(null);
        } else {
            this.setFreeShipping(Objects.isNull(this.getShippingRateId()));
        }
    }

    @Override
    public void setShippingRateId(String shippingRateId) throws ProductException {
        if (Objects.nonNull(shippingRateId)) {
            this.setFixedShippingCost(null);
        }
        this.setFreeShipping(Objects.isNull(shippingRateId) && Objects.isNull(this.getFixedShippingCost()));
    }

    private void setMinPrice() {
        double minPrice = this.getVariants().stream()
                .map(ProductVariant::getPrice)
                .mapToDouble(BigDecimal::doubleValue)
                .min()
                .orElse(0);
        this.setPrice(BigDecimal.valueOf(minPrice));
    }

    private void checkInventory() {
        this.setInventoryQuantity(
                this.getVariants().stream().mapToInt(ProductVariant::getInventoryQuantity).sum());
        this.setInventoryStatus(
                this.getInventoryQuantity() == 0
                        ? InventoryStatus.OUT_OF_STOCK : InventoryStatus.IN_STOCK);
    }

    @Override
    public ProductOrigin createOrigin() {
        return new DefaultProductOrigin();
    }

    private void effectVariants() {
        Positions.sort(this.getVariants());
        this.checkInventory();
        this.setMinPrice();
    }

    private void addToVariants(ProductVariant variant) {
        variant.setProductId(this.getId());
        variant.setStoreId(this.getStoreId());
        this.getVariants().add(variant);
    }

    @Override
    public void addVariant(ProductVariant variant) {
        this.addToVariants(variant);
        this.effectVariants();
    }

    @Override
    public void addVariants(List<ProductVariant> variants) {
        ListUtils.emptyIfNull(variants).forEach(this::addToVariants);
        this.effectVariants();
    }

    @Override
    public ProductVariant getVariant(String variantId) {
        return this.findVariant(variantId)
                .orElseThrow(() -> new ProductException(ProductMessages.Variant.notFound()));
    }

    @Override
    public Optional<ProductVariant> findVariant(String id) {
        return this.getVariants()
                .stream()
                .filter(variant -> Objects.equals(variant.getId(), id))
                .findFirst();
    }

    @Override
    public Optional<ProductVariant> selectionVariant(List<OptionSelection> selections) {
        return this.getVariants()
                .stream()
                .filter(variant -> OptionSelections.equals(selections, variant.getOptionSelections()))
                .findFirst();
    }

    private void removeFromVariants(ProductVariant variant) {
        var removed = this.getVariants().remove(variant);
        Assert.state(removed, ProductMessages.Variant::notFound);
    }

    @Override
    public void removeVariant(ProductVariant variant) {
        this.removeFromVariants(variant);
        this.effectVariants();
    }

    @Override
    public void removeVariants(List<ProductVariant> variants) {
        ListUtils.emptyIfNull(variants).forEach(this::removeFromVariants);
        this.effectVariants();
    }

    @Override
    public void clearVariants() {
        this.getVariants().clear();
        this.effectVariants();
    }

    @Override
    public void adjustInventoryQuantity(String variantId, int quantityDelta) {
        this.getVariant(variantId).adjustInventoryQuantity(quantityDelta);
        this.checkInventory();
    }

    private Optional<Option> obtainOption(String id) {
        return this.getOptions().stream()
                .filter(option -> Objects.equals(option.getId(), id))
                .findFirst();
    }

    private void setOption(Option source, Option target) {
        target.setName(target.getName());
        target.addValues(source.getValues());
    }

    @Override
    public void addOption(Option option) {
        if (Objects.isNull(option.getId())) {
            this.getOptions().add(option);
        } else {
            this.obtainOption(option.getId())
                    .ifPresentOrElse(target -> setOption(option, target), () -> this.getOptions().add(option));
        }
        Positions.sort(this.getOptions());
    }

    @Override
    public Optional<Option> getOption(String name) {
        return this.getOptions().stream().filter(option -> Objects.equals(option.getName(), name)).findFirst();
    }

    @Override
    public Optional<OptionSelection> selectOption(final String name, final String label) {
        return this.getOption(name)
                .map(option -> Map.entry(option, option.getValue(label)))
                .map(entry -> new DefaultOptionSelection(entry.getKey().getId(), name, entry.getValue().getId(), label));
    }

    @Override
    public void updateOptions(List<Option> options) {
        this.getOptions().clear();
        ListUtils.emptyIfNull(options).forEach(this::addOption);
    }

    @Override
    public void addAttribute(ProductAttribute attribute) {
        this.getAttributes().add(attribute);
        Positions.sort(this.getAttributes());
    }

    @Override
    public void addAttributes(List<ProductAttribute> attributes) {
        ListUtils.emptyIfNull(attributes).forEach(this::addAttribute);
    }

    @Override
    public Optional<ProductAttribute> getAttribute(String namespace, String name) {
        return this.getAttributes()
                .stream()
                .filter(attribute ->
                        Objects.equals(attribute.getNamespace(), namespace)
                                && Objects.equals(attribute.getName(), name))
                .findFirst();
    }

    @Override
    public void removeAttribute(ProductAttribute attribute) {
        this.getAttributes().remove(attribute);
    }

    @Override
    public void clearAttributes() {
        this.getAttributes().clear();
    }

    @Override
    public void addImageUrl(String imageUrl) {
        this.getImageUrls().add(imageUrl);
    }

    @Override
    public void removeImageUrl(String url) {
        this.getImageUrls().remove(url);
    }

    @Override
    public void addVideoUrl(String video) {
        this.getVideoUrls().add(video);
    }

    @Override
    public void removeVideoUrl(String url) {
        this.getVideoUrls().remove(url);
    }

    @Override
    public void create() {
        this.setStatus(ProductStatus.ACTIVE);
        this.setCreatedTime(new Date());
    }

    @Override
    public void publish() {
        if (ProductStatus.ACTIVE.equals(this.getStatus())) {
            throw new ProductException("The product has been published");
        }
        this.setPublishedTime(new Date());
        this.setStatus(ProductStatus.ACTIVE);
    }

    @Override
    public void unpublish() {
        if (ProductStatus.ARCHIVED.equals(this.getStatus())) {
            throw new ProductException("The product has been archived");
        }
        this.setPublishedTime(null);
        this.setStatus(ProductStatus.ARCHIVED);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {

        protected final Product product;

        BuilderSupport(Product product) {
            this.product = product;
        }

        @Override
        public Builder storeId(String storeId) {
            this.product.setStoreId(storeId);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.product.setName(name);
            return this;
        }

        @Override
        public Builder description(String description) {
            this.product.setDescription(description);
            return this;
        }

        @Override
        public Builder type(ProductType type) {
            this.product.setType(type);
            return this;
        }

        @Override
        public Builder status(ProductStatus status) {
            this.product.setStatus(status);
            return this;
        }

        @Override
        public Builder categories(List<String> categories) {
            this.product.setCategories(categories);
            return this;
        }

        @Override
        public Builder brandId(String brandId) {
            this.product.setBrandId(brandId);
            return this;
        }

        @Override
        public Builder freeShipping() {
            this.product.freeShipping();
            return this;
        }

        @Override
        public Builder freeShipping(Boolean freeShipping) {
            if (Objects.nonNull(freeShipping) && freeShipping) {
                this.freeShipping();
            }
            return this;
        }

        @Override
        public Builder fixedShippingCost(BigDecimal fixedShippingCost) {
            this.product.setFixedShippingCost(fixedShippingCost);
            return this;
        }

        @Override
        public Builder fixedShippingCost(double fixedShippingCost) {
            return this.fixedShippingCost(BigDecimal.valueOf(fixedShippingCost));
        }

        @Override
        public Builder shippingRateId(String shippingRateId) {
            this.product.setShippingRateId(shippingRateId);
            return this;
        }

        @Override
        public Builder origin(ProductOrigin shippingOrigin) {
            this.product.setOrigin(shippingOrigin);
            return this;
        }

        @Override
        public Builder origin(Function<Product, ProductOrigin> shippingOrigin) {
            return this.origin(shippingOrigin.apply(this.product));
        }

        @Override
        public Builder origin(Supplier<ProductOrigin> supplier) {
            return this.origin(supplier.get());
        }

        @Override
        public Builder totalSales(long sales) {
            this.product.setTotalSales(sales);
            return this;
        }

        @Override
        public Builder monthlySales(long sales) {
            this.product.setMonthlySales(sales);
            return this;
        }

        @Override
        public Builder collections(Set<String> collections) {
            this.product.setCollections(collections);
            return this;
        }

        @Override
        public Builder body(String body) {
            this.product.setBody(body);
            return this;
        }

        @Override
        public Builder imageUrl(String image) {
            this.product.addImageUrl(image);
            return this;
        }

        @Override
        public Builder imageUrls(List<String> images) {
            this.product.setImageUrls(images);
            return this;
        }

        @Override
        public Builder videoUrl(String video) {
            this.product.addVideoUrl(video);
            return this;
        }

        @Override
        public Builder videoUrls(List<String> videos) {
            this.product.setVideoUrls(videos);
            return this;
        }

        @Override
        public Builder option(Option option) {
            this.product.addOption(option);
            return this;
        }

        @Override
        public Builder option(Function<Product, Option> option) {
            return this.option(option.apply(this.product));
        }

        @Override
        public Builder options(List<Option> options) {
            this.product.updateOptions(options);
            return this;
        }

        @Override
        public Builder options(Function<Product, List<Option>> options) {
            return this.options(options.apply(this.product));
        }

        @Override
        public Builder variant(ProductVariant variant) {
            this.product.addVariant(variant);
            return this;
        }

        @Override
        public Builder variant(Function<Product, ProductVariant> variant) {
            return this.variant(variant.apply(this.product));
        }

        @Override
        public Builder variants(Function<Product, List<ProductVariant>> function) {
            this.product.addVariants(function.apply(this.product));
            return this;
        }

        @Override
        public Builder variants(Supplier<List<ProductVariant>> supplier) {
            this.product.addVariants(supplier.get());
            return this;
        }

        @Override
        public Builder attribute(ProductAttribute attribute) {
            this.product.addAttribute(attribute);
            return this;
        }

        @Override
        public Builder attribute(Function<Product, ProductAttribute> attribute) {
            return this.attribute(attribute.apply(this.product));
        }

        @Override
        public Builder attributes(Function<Product, List<ProductAttribute>> attributes) {
            this.product.addAttributes(attributes.apply(this.product));
            return this;
        }

        @Override
        public Builder attributes(Supplier<List<ProductAttribute>> attributes) {
            this.product.addAttributes(attributes.get());
            return this;
        }

        @Override
        public Builder create() {
            this.product.create();
            return this;
        }

        @Override
        public Builder publish() {
            this.product.publish();
            return this;
        }

        @Override
        public Builder unpublish() {
            this.product.unpublish();
            return this;
        }

        @Override
        public Product build() {
            return this.product;
        }
    }
}
