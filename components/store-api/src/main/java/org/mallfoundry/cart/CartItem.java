package org.mallfoundry.cart;

import org.mallfoundry.catalog.OptionSelection;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface CartItem extends Serializable {

    String getId();

    String getStoreId();

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    String getName();

    void setName(String name);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    List<OptionSelection> getOptionSelections();

    void setOptionSelections(List<OptionSelection> optionSelections);

    int getQuantity();

    void setQuantity(int quantity);

    Date getAddedTime();

    default Builder toBuilder() {
        return new Builder(this);
    }

    class Builder {

        private final CartItem item;

        public Builder(CartItem item) {
            this.item = item;
        }

        public Builder productId(String productId) {
            this.item.setProductId(productId);
            return this;
        }

        public Builder variantId(String variantId) {
            this.item.setVariantId(variantId);
            return this;
        }

        public Builder quantity(int quantity) {
            this.item.setQuantity(quantity);
            return this;
        }

        public Builder name(String name) {
            this.item.setName(name);
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.item.setImageUrl(imageUrl);
            return this;
        }

        public Builder optionSelections(List<OptionSelection> optionSelections) {
            this.item.setOptionSelections(optionSelections);
            return this;
        }

        public CartItem build() {
            return this.item;
        }
    }
}