package org.mallfoundry.order;

import org.mallfoundry.util.ObjectBuilder;

public interface ShipmentItem {

    String getId();

    void setId(String id);

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    int getQuantity();

    void setQuantity(int quantity);

    String getName();

    void setName(String name);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<ShipmentItem> {

        Builder productId(String productId);

        Builder variantId(String variantId);

        Builder quantity(int quantity);

        Builder name(String name);

        Builder imageUrl(String imageUrl);
    }

    abstract class BuilderSupport implements Builder {

        protected final ShipmentItem item;

        public BuilderSupport(ShipmentItem item) {
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
        public Builder name(String name) {
            this.item.setName(name);
            return this;
        }

        @Override
        public Builder imageUrl(String imageUrl) {
            this.item.setImageUrl(imageUrl);
            return this;
        }

        @Override
        public ShipmentItem build() {
            return this.item;
        }
    }
}
