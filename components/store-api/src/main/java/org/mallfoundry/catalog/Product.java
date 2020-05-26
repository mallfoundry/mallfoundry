package org.mallfoundry.catalog;

import org.mallfoundry.inventory.InventoryStatus;
import org.mallfoundry.util.ObjectBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public interface Product extends Serializable {

    String getId();

    void setId(String id);

    String getStoreId();

    void setStoreId(String storeId);

    String getName();

    void setName(String name);

    ProductType getType();

    void setType(ProductType type);

    ProductStatus getStatus();

    void setStatus(ProductStatus status);

    String getDescription();

    void setDescription(String description);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    BigDecimal getMarketPrice();

    void setMarketPrice(BigDecimal marketPrice);

    Set<String> getCollections();

    void setCollections(Set<String> collections);

    List<ProductOption> getOptions();

    List<String> getImageUrls();

    List<String> getVideoUrls();

    boolean isFreeShipping();

    void setFreeShipping(boolean freeShipping);

    BigDecimal getFixedShippingCost();

    void setFixedShippingCost(BigDecimal fixedShippingCost);

    String getShippingRateId();

    void setShippingRateId(String shippingRateId);

    int getInventoryQuantity();

    InventoryStatus getInventoryStatus();

    List<ProductVariant> getVariants();

    Date getCreatedTime();

    Optional<ProductVariant> getVariant(String variantId);

    ProductVariant createVariant(String id);

    void addVariant(ProductVariant variant);

    void adjustVariantInventoryQuantity(String variantId, int adjustQuantity);

    ProductOption createOption(String id);

    Optional<ProductOption> getOption(String name);

    void addOption(ProductOption option);

    Optional<OptionSelection> selectOption(String name, String label);

    ProductAttribute createAttribute(String name, String value);

    ProductAttribute createAttribute(String namespace, String name, String value);

    Optional<ProductAttribute> getAttribute(String namespace, String name);

    void addAttribute(ProductAttribute attribute);

    void addImageUrl(String url);

    void addVideoUrl(String url);

    void create();

    default Builder toBuilder() {
        return new BuilderSupport(this);
    }


    interface Builder extends ObjectBuilder<Product> {

        Builder storeId(String storeId);

        Builder name(String name);

        Builder type(ProductType type);

        Builder status(ProductStatus status);

        Builder imageUrl(String image);

        Builder videoUrl(String video);

        Builder option(ProductOption option);

        Builder option(Function<Product, ProductOption> option);

        Builder variant(ProductVariant variant);

        Builder variant(Function<Product, ProductVariant> variant);

        Builder attribute(ProductAttribute attribute);

        Builder attribute(Function<Product, ProductAttribute> attribute);

        Builder create();
    }

    class BuilderSupport implements Builder {

        private final Product product;

        public BuilderSupport(Product product) {
            this.product = product;
        }

        public Builder storeId(String storeId) {
            this.product.setStoreId(storeId);
            return this;
        }

        public Builder name(String name) {
            this.product.setName(name);
            return this;
        }

        public Builder type(ProductType type) {
            this.product.setType(type);
            return this;
        }

        public Builder status(ProductStatus status) {
            this.product.setStatus(status);
            return this;
        }

        public Builder imageUrl(String image) {
            this.product.addImageUrl(image);
            return this;
        }

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

        public Product build() {
            return this.product;
        }
    }
}
