package com.mallfoundry.cart;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface CartItem extends Serializable {

    String getId();

    String getStoreId();

    String getProductId();

    String getVariantId();

    String getName();

    void setName(String name);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    List<String> getOptionValues();

    void setOptionValues(List<String> optionValues);

    int getQuantity();

    void setQuantity(int quantity);

    void increaseQuantity(int quantity);

    void decrementQuantity(int quantity);

    boolean isEmpty();

    Date getAddedTime();

    default Builder toBuilder() {
        return new Builder(this);
    }

    class Builder {
        private final CartItem item;

        public Builder(CartItem item) {
            this.item = item;
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

        public Builder optionValues(List<String> optionValues) {
            this.item.setOptionValues(optionValues);
            return this;
        }

        public CartItem build() {
            return this.item;
        }
    }


}
