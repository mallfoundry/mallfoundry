package org.mallfoundry.customer;

import org.mallfoundry.util.ObjectBuilder;

import java.math.BigDecimal;
import java.util.Date;

public interface BrowsingProduct {

    String getId();

    String getBrowserId();

    void setBrowserId(String browserId);

    String getName();

    void setName(String name);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);


    Date getBrowsingTime();

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<BrowsingProduct> {

        Builder browserId(String browserId);

        Builder name(String name);

        Builder price(BigDecimal price);
    }

    abstract class BuilderSupport implements Builder {

        protected final BrowsingProduct browsingProduct;

        public BuilderSupport(BrowsingProduct browsingProduct) {
            this.browsingProduct = browsingProduct;
        }

        public Builder browserId(String browserId) {
            this.browsingProduct.setBrowserId(browserId);
            return this;
        }

        public Builder name(String name) {
            this.browsingProduct.setName(name);
            return this;
        }

        public Builder price(BigDecimal price) {
            this.browsingProduct.setPrice(price);
            return this;
        }

        @Override
        public BrowsingProduct build() {
            return this.browsingProduct;
        }
    }

}
