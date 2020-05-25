package com.mallfoundry.order;

import com.mallfoundry.catalog.OptionSelection;
import com.mallfoundry.util.ObjectBuilder;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItem {

    String getId();

    String getStoreId();

    void setStoreId(String storeId);

    String getProductId();

    void setProductId(String productId);

    String getVariantId();

    void setVariantId(String variantId);

    List<OptionSelection> getOptionSelections();

    void setOptionSelections(List<OptionSelection> optionSelections);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    String getName();

    void setName(String name);

    int getQuantity();

    void setQuantity(int quantity);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    BigDecimal getShippingCost();

    void setShippingCost(BigDecimal shippingCost);

    BigDecimal getDiscountShippingCost();

    void setDiscountShippingCost(BigDecimal discountShippingCost);

    BigDecimal getSubtotalAmount();

    BigDecimal getOriginalAmount();

    BigDecimal getDiscountAmount();

    void setDiscountAmount(BigDecimal discountAmount);

    BigDecimal getActualAmount();

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }


    interface Builder extends ObjectBuilder<OrderItem> {

        Builder storeId(String storeId);

        Builder productId(String productId);

        Builder variantId(String variantId);

        Builder optionSelections(List<OptionSelection> optionSelections);

        Builder imageUrl(String imageUrl);

        Builder name(String name);

        Builder quantity(int quantity);

        Builder price(BigDecimal price);
    }

    abstract class BuilderSupport implements Builder {

        private final OrderItem item;

        public BuilderSupport(OrderItem item) {
            this.item = item;
        }

        @Override
        public Builder storeId(String storeId) {
            this.item.setStoreId(storeId);
            return this;
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
        public Builder optionSelections(List<OptionSelection> optionSelections) {
            this.item.setOptionSelections(optionSelections);
            return this;
        }

        @Override
        public Builder imageUrl(String imageUrl) {
            this.item.setImageUrl(imageUrl);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.item.setName(name);
            return this;
        }

        @Override
        public Builder quantity(int quantity) {
            this.item.setQuantity(quantity);
            return this;
        }

        @Override
        public Builder price(BigDecimal price) {
            this.item.setPrice(price);
            return this;
        }

        @Override
        public OrderItem build() {
            return this.item;
        }
    }
}
