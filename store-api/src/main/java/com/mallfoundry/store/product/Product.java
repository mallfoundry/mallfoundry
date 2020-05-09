package com.mallfoundry.store.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface Product extends Serializable {

    String getId();

    void setId(String id);

    String getStoreId();

    void setStoreId(String storeId);

    String getName();

    void setName(String name);

    ProductType getType();

    void setType(ProductType type);

    String getDescription();

    void setDescription(String description);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    BigDecimal getMarketPrice();

    void setMarketPrice(BigDecimal marketPrice);

    List<String> getImageUrls();

    List<String> getVideoUrls();

    boolean isFreeShipping();

    void setFreeShipping(boolean freeShipping);

    BigDecimal getFixedShippingCost();

    void setFixedShippingCost(BigDecimal fixedShippingCost);

    String getShippingRateId();

    void setShippingRateId(String shippingRateId);

    Date getCreatedTime();

    List<ProductVariant> getVariants();

    Optional<ProductVariant> getVariant(String variantId);

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
