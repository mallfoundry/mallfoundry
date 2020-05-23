package com.mallfoundry.store.product;

import com.mallfoundry.util.Position;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface ProductVariant extends Serializable, Position {

    String getId();

    void setId(String id);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    List<String> getOptionValues();

    void setOptionValues(List<String> optionValues);

    List<String> getImageUrls();

    void setImageUrls(List<String> imageUrls);

    String getFirstImageUrl();

    BigDecimal getMarketPrice();

    void setMarketPrice(BigDecimal marketPrice);

    int getInventoryQuantity();

    void setInventoryQuantity(int inventoryQuantity);

    void adjustInventoryQuantity(int adjustQuantity);

    InventoryStatus getInventoryStatus();

    Builder toBuilder();

    class Builder {

        private final ProductVariant variant;

        public Builder(ProductVariant variant) {
            this.variant = variant;
        }

        public Builder price(double price) {
            this.variant.setPrice(BigDecimal.valueOf(price));
            return this;
        }

        public Builder marketPrice(double marketPrice) {
            this.variant.setMarketPrice(BigDecimal.valueOf(marketPrice));
            return this;
        }

        public Builder inventoryQuantity(int inventoryQuantity) {
            this.variant.setInventoryQuantity(inventoryQuantity);
            return this;
        }

        public Builder position(int position) {
            this.variant.setPosition(position);
            return this;
        }

        public Builder optionValues(List<String> optionValues) {
            this.variant.setOptionValues(optionValues);
            return this;
        }

        public Builder imageUrls(List<String> imageUrls) {
            this.variant.setImageUrls(imageUrls);
            return this;
        }

        public ProductVariant build() {
            return this.variant;
        }
    }
}
