package com.mallfoundry.checkout;

import com.mallfoundry.util.ObjectBuilder;

public interface CheckoutItem {

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    int getQuantity();

    void setQuantity(int quantity);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<CheckoutItem> {

        Builder productId(String productId);

        Builder variantId(String variantId);

        Builder quantity(int quantity);
    }

    abstract class BuilderSupport implements Builder {

        protected final CheckoutItem item;

        public BuilderSupport(CheckoutItem item) {
            this.item = item;
        }

        @Override
        public Builder productId(String productId) {
            this.item.setProductId(productId);
            return this;
        }

        @Override
        public Builder variantId(String variantId) {
            this.item.setVariantId(variantId);
            return this;
        }

        @Override
        public Builder quantity(int quantity) {
            this.item.setQuantity(quantity);
            return this;
        }

        @Override
        public CheckoutItem build() {
            return this.item;
        }
    }

}
