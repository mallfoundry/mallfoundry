package com.mallfoundry.rest.store.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    Set<String> getCollectionIds();

    void setCollectionIds(Set<String> ids);

    List<String> getImageUrls();

    String getFirstImageUrl();

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

    Optional<ProductVariant> getVariant(String variantId);

    Date getCreatedTime();

    void addVariant(ProductVariant variant);

    ProductOption createOption(String name);

    ProductAttribute createAttribute(String name, String value);

    ProductAttribute createAttribute(String namespace, String name, String value);

    void addImageUrl(String url);

    void addVideoUrl(String url);

    Builder toBuilder();

    class Builder {

        private final Product product;

        public Builder(Product product) {
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

        public Builder addImageUrl(String image) {
            this.product.addImageUrl(image);
            return this;
        }

        public Builder addVideoUrl(String video) {
            this.product.addVideoUrl(video);
            return this;
        }

        public Product build() {
            return this.product;
        }
    }
}
