package org.mallfoundry.catalog.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mallfoundry.catalog.DefaultOptionSelection;
import org.mallfoundry.catalog.OptionSelection;
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

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.trim;

@Getter
@Setter
@NoArgsConstructor
public abstract class ProductSupport implements MutableProduct {

    public ProductSupport(String id) {
        this.setId(id);
    }

    @Override
    public void setId(String id) {
        Assert.isTrue(isBlank(this.getId()), ProductMessages.empty("id"));
        this.doSetId(trim(id));
    }

    @Override
    public void setName(String name) {
        Assert.isTrue(isNotBlank(name), ProductMessages.notEmpty("name"));
        this.doSetName(StringUtils.trim(name));
    }

    @Override
    public void freeShipping() {
        this.setFreeShipping(true);
    }

    @Override
    public void setFreeShipping(boolean freeShipping) {
        this.doSetFreeShipping(freeShipping);
        if (this.isFreeShipping()) {
            this.setFixedShippingCost(null);
            this.setShippingRateId(null);
        }
    }

    @Override
    public void setInventoryStatus(InventoryStatus status) {
        Assert.notNull(status, ProductMessages.notEmpty("status"));
        this.doSetInventoryStatus(status);
    }

    @Override
    public void setOptions(List<ProductOption> options) {
        Assert.isTrue(CollectionUtils.isNotEmpty(options), ProductMessages.notEmpty("options"));
        this.doSetOptions(options);
    }

    @Override
    public void setVariants(List<ProductVariant> variants) {
        Assert.isTrue(CollectionUtils.isNotEmpty(variants), ProductMessages.notEmpty("variants"));
        this.doSetVariants(variants);
    }

    @Override
    public void setCreatedTime(Date createdTime) {
        Assert.notNull(createdTime, ProductMessages.notEmpty("createdTime"));
        this.doSetCreatedTime(createdTime);
    }

    @Override
    public void setStoreId(String storeId) {
        Assert.isTrue(isNotBlank(storeId), ProductMessages.notEmpty("storeId"));
        this.doSetStoreId(trim(storeId));
    }

    @Override
    public void setType(ProductType type) {
        Assert.notNull(type, ProductMessages.notEmpty("storeId"));
        this.doSetType(type);
    }

    @Override
    public void setStatus(ProductStatus status) {
        Assert.notNull(status, ProductMessages.notEmpty("status"));
        this.doSetStatus(status);
    }

    @Override
    public void setDescription(String description) {
        Assert.isTrue(isNotBlank(description), ProductMessages.notEmpty("description"));
        this.doSetDescription(trim(description));
    }

    @Override
    public void setCategoryId(String categoryId) {
        Assert.isTrue(isNotBlank(categoryId), ProductMessages.notEmpty("categoryId"));
        this.doSetCategoryId(trim(categoryId));
    }

    @Override
    public void setBrandId(String brandId) {
        Assert.isTrue(isNotBlank(brandId), ProductMessages.notEmpty("brandId"));
        this.doSetBrandId(trim(brandId));
    }

    @Override
    public void setCollections(Set<String> collections) {
        Assert.isTrue(CollectionUtils.isNotEmpty(collections), ProductMessages.notEmpty("collections"));
        this.doSetCollections(collections);
    }

    @Override
    public void setShippingOrigin(ProductShippingOrigin shippingOrigin) {
        Assert.notNull(shippingOrigin, ProductMessages.notEmpty("shippingOrigin"));
        this.doSetShippingOrigin(shippingOrigin);
    }

    @Override
    public void setFixedShippingCost(BigDecimal fixedShippingCost) throws ProductException {
        if (!this.isFreeShipping()) {
            Assert.notNull(fixedShippingCost, ProductMessages.notEmpty("fixedShippingCost"));
            this.setFreeShipping(false);
        }
        this.doSetFixedShippingCost(fixedShippingCost);
    }

    @Override
    public void setShippingRateId(String shippingRateId) throws ProductException {
        if (!this.isFreeShipping()) {
            Assert.isTrue(isNotBlank(shippingRateId), ProductMessages.notEmpty("shippingRateId"));
            this.setFreeShipping(false);
        }
        this.doSetShippingRateId(trim(shippingRateId));
    }

    @Override
    public void setAttributes(List<ProductAttribute> attributes) {
        Assert.isTrue(CollectionUtils.isNotEmpty(attributes), ProductMessages.notEmpty("attributes"));
        this.doSetAttributes(attributes);
    }

    @Override
    public void setTotalSales(long sales) {
        Assert.isTrue(sales >= 0, ProductMessages.greaterThanX("totalSales ", sales));
        this.doSetTotalSales(sales);
    }

    @Override
    public void setMonthlySales(long sales) {
        Assert.isTrue(sales >= 0, ProductMessages.greaterThanX("monthlySales ", sales));
        this.doSetMonthlySales(sales);
    }

    @Override
    public void setVersion(long version) {
        this.doSetVersion(version);
    }

    @Override
    public ProductShippingOrigin createShippingOrigin() {
        return new DefaultProductShippingOrigin();
    }

    @Override
    public void adjustTotalSales(long sales) {
        this.setTotalSales(this.getTotalSales() + sales);
    }

    @Override
    public void adjustMonthlySales(long sales) {
        this.setMonthlySales(this.getMonthlySales() + sales);
    }

    @Override
    public void addVariant(ProductVariant variant) {
        this.getVariants().add(variant);
        Positions.sort(this.getVariants());
    }

    @Override
    public Optional<ProductVariant> getVariant(String id) {
        return this.getVariants()
                .stream()
                .filter(variant -> Objects.equals(variant.getId(), id))
                .findFirst();
    }

    @Override
    public void removeVariant(ProductVariant variant) {
        var removed =
                Objects.requireNonNull(this.getVariants(), ProductMessages.notEmpty("variants"))
                        .remove(variant);
        Assert.isTrue(removed, ProductMessages.variantNotFound());
    }

    @Override
    public void adjustInventoryQuantity(String variantId, int quantityDelta) {
        this.getVariant(variantId)
                .orElseThrow(() -> new ProductException(ProductMessages.variantNotFound().get()))
                .adjustInventoryQuantity(quantityDelta);
    }

    @Override
    public void addOption(ProductOption option) {
        this.getOptions().add(option);
        Positions.sort(this.getOptions());
    }

    @Override
    public Optional<ProductOption> getOption(String name) {
        return this.getOptions().stream().filter(option -> Objects.equals(option.getName(), name)).findFirst();
    }

    @Override
    public Optional<OptionSelection> selectOption(final String name, final String label) {
        return this.getOption(name)
                .map(option -> Map.entry(option, option.getValue(label).orElseThrow()))
                .map(entry -> new DefaultOptionSelection(entry.getKey().getId(), name, entry.getValue().getId(), label));
    }

    @Override
    public void removeOption(ProductOption option) {
        var removed = this.getOptions().remove(option);
        Assert.isTrue(removed, ProductMessages.optionNotFound());
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
    public void setImageUrls(List<String> imageUrls) {
        Assert.isTrue(CollectionUtils.isNotEmpty(imageUrls), ProductMessages.notEmpty("imageUrls"));
        this.doSetImageUrls(imageUrls);
    }

    @Override
    public void setVideoUrls(List<String> videoUrls) {
        Assert.isTrue(CollectionUtils.isNotEmpty(videoUrls), ProductMessages.notEmpty("videoUrls"));
        this.doSetVideoUrls(videoUrls);
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
    public void addAttribute(ProductAttribute attribute) {
        this.getAttributes().add(attribute);
        Positions.sort(this.getAttributes());
    }

    @Override
    public void create() {
        this.setCreatedTime(new Date());
    }

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected void doSetId(String id) {
    }

    protected void doSetName(String name) {
    }

    protected void doSetFreeShipping(boolean freeShipping) {
    }

    protected void doSetInventoryStatus(InventoryStatus status) {
    }

    protected void doSetOptions(List<ProductOption> options) {
    }

    protected void doSetVariants(List<ProductVariant> variants) {
    }

    protected void doSetCreatedTime(Date createdTime) {
    }

    protected void doSetStoreId(String storeId) {
    }

    protected void doSetType(ProductType type) {
    }

    protected void doSetStatus(ProductStatus status) {
    }

    protected void doSetDescription(String description) {
    }

    protected void doSetCategoryId(String categoryId) {
    }

    protected void doSetBrandId(String categoryId) {
    }

    protected void doSetCollections(Set<String> collections) {
    }

    protected void doSetImageUrls(List<String> imageUrls) {
    }

    protected void doSetVideoUrls(List<String> videoUrls) {
    }

    protected void doSetShippingOrigin(ProductShippingOrigin shippingOrigin) {
    }

    protected void doSetFixedShippingCost(BigDecimal fixedShippingCost) {
    }

    protected void doSetShippingRateId(String shippingRateId) {
    }

    protected void doSetAttributes(List<ProductAttribute> attributes) {
    }

    protected void doSetTotalSales(long sales) {
    }

    protected void doSetMonthlySales(long sales) {
    }

    protected void doSetVersion(long version) {
    }

    abstract static class BuilderSupport implements Builder {

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
        public Builder freeShipping() {
            this.product.freeShipping();
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
        public Builder shippingOrigin(ProductShippingOrigin shippingOrigin) {
            this.product.setShippingOrigin(shippingOrigin);
            return this;
        }

        @Override
        public Builder shippingOrigin(Function<Product, ProductShippingOrigin> shippingOrigin) {
            return this.shippingOrigin(shippingOrigin.apply(this.product));
        }

        @Override
        public Builder adjustTotalSales(long sales) {
            this.product.adjustTotalSales(sales);
            return this;
        }

        @Override
        public Builder adjustMonthlySales(long sales) {
            this.product.adjustMonthlySales(sales);
            return this;
        }

        @Override
        public Builder collections(Set<String> collections) {
            this.product.setCollections(collections);
            return this;
        }

        @Override
        public Builder imageUrl(String image) {
            this.product.addImageUrl(image);
            return this;
        }

        @Override
        public Builder videoUrl(String video) {
            this.product.addVideoUrl(video);
            return this;
        }

        @Override
        public Builder option(ProductOption option) {
            this.product.addOption(option);
            return this;
        }

        @Override
        public Builder option(Function<Product, ProductOption> option) {
            return this.option(option.apply(this.product));
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
        public Builder attribute(ProductAttribute attribute) {
            this.product.addAttribute(attribute);
            return this;
        }

        @Override
        public Builder attribute(Function<Product, ProductAttribute> attribute) {
            return this.attribute(attribute.apply(this.product));
        }

        @Override
        public Builder create() {
            this.product.create();
            return this;
        }

        @Override
        public Product build() {
            return this.product;
        }
    }
}
