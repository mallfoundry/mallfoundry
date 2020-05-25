package com.mallfoundry.catalog;

import com.mallfoundry.inventory.InventoryException;
import com.mallfoundry.inventory.InventoryStatus;
import com.mallfoundry.util.Position;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface ProductVariant extends Serializable, Position {

    String getId();

    void setId(String id);

    String getStoreId();

    String getProductId();

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    List<OptionSelection> getOptionSelections();

    void setOptionSelections(List<OptionSelection> optionSelections);

    List<String> getImageUrls();

    void setImageUrls(List<String> imageUrls);

    BigDecimal getMarketPrice();

    void setMarketPrice(BigDecimal marketPrice);

    int getInventoryQuantity();

    void setInventoryQuantity(int inventoryQuantity) throws InventoryException;

    void adjustInventoryQuantity(int quantityDelta) throws InventoryException;

    InventoryStatus getInventoryStatus();

    default Builder toBuilder() {
        return new Builder(this);
    }

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

        public Builder optionSelections(List<OptionSelection> optionSelections) {
            this.variant.setOptionSelections(optionSelections);
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
