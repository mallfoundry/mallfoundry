package org.mallfoundry.browsing;

import org.mallfoundry.util.ObjectBuilder;

import java.util.Date;

public interface BrowsingProduct {

    String getBrowserId();

    String getProductId();

    void setProductId(String productId);

    Date getBrowsingTime();

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<BrowsingProduct> {
        Builder productId(String productId);
    }

    abstract class BuilderSupport implements Builder {

        protected final BrowsingProduct browsingProduct;

        public BuilderSupport(BrowsingProduct browsingProduct) {
            this.browsingProduct = browsingProduct;
        }

        public Builder productId(String productId) {
            this.browsingProduct.setProductId(productId);
            return this;
        }

        @Override
        public BrowsingProduct build() {
            return this.browsingProduct;
        }
    }

}
