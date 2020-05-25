package org.mallfoundry.inventory;

import org.mallfoundry.util.ObjectBuilder;

public interface InventoryAdjustment {

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    int getQuantityDelta();

    void setQuantityDelta(int quantityDelta);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<InventoryAdjustment> {

        Builder productId(String productId);

        Builder variantId(String variantId);

        Builder quantityDelta(int quantityDelta);
    }

    abstract class BuilderSupport implements Builder {

        protected final InventoryAdjustment adjustment;

        protected BuilderSupport(InventoryAdjustment adjustment) {
            this.adjustment = adjustment;
        }

        @Override
        public Builder productId(String productId) {
            this.adjustment.setProductId(productId);
            return this;
        }

        @Override
        public Builder variantId(String variantId) {
            this.adjustment.setVariantId(variantId);
            return this;
        }

        @Override
        public Builder quantityDelta(int quantityDelta) {
            this.adjustment.setQuantityDelta(quantityDelta);
            return this;
        }

        @Override
        public InventoryAdjustment build() {
            return this.adjustment;
        }
    }
}
