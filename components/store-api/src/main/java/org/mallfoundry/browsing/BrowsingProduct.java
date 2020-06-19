package org.mallfoundry.browsing;

import org.mallfoundry.util.ObjectBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public interface BrowsingProduct extends Serializable {

    String getId();

    String getBrowserId();

    void setBrowserId(String browserId);

    String getName();

    void setName(String name);

    String getImageUrl();

    void setImageUrl(String imageUrl);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    int getHits();

    Date getBrowsingTime();

    int hit();

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<BrowsingProduct> {

        Builder browserId(String browserId);

        Builder name(String name);

        Builder imageUrl(String imageUrl);

        Builder price(BigDecimal price);

        Builder hit();
    }

    abstract class BuilderSupport implements Builder {

        protected final BrowsingProduct browsingProduct;

        public BuilderSupport(BrowsingProduct browsingProduct) {
            this.browsingProduct = browsingProduct;
        }

        @Override
        public Builder browserId(String browserId) {
            this.browsingProduct.setBrowserId(browserId);
            return this;
        }

        @Override
        public Builder name(String name) {
            this.browsingProduct.setName(name);
            return this;
        }

        @Override
        public Builder imageUrl(String imageUrl) {
            this.browsingProduct.setImageUrl(imageUrl);
            return this;
        }

        @Override
        public Builder price(BigDecimal price) {
            this.browsingProduct.setPrice(price);
            return this;
        }


        @Override
        public Builder hit() {
            this.browsingProduct.hit();
            return this;
        }

        @Override
        public BrowsingProduct build() {
            return this.browsingProduct;
        }
    }

}
