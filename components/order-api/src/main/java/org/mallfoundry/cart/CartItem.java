/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.cart;

import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.util.ObjectBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
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

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    int getQuantity();

    void setQuantity(int quantity);

    void adjustQuantity(int quantityDelta) throws CartException;

    boolean isChecked();

    void check();

    void uncheck();

    Date getAddedTime();

    default Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    interface Builder extends ObjectBuilder<CartItem> {

        Builder productId(String productId);

        Builder variantId(String variantId);

        Builder quantity(int quantity);

        Builder name(String name);

        Builder imageUrl(String imageUrl);

        Builder checked(boolean checked);

        Builder check();

        Builder uncheck();

        Builder optionSelections(List<OptionSelection> optionSelections);
    }

    abstract class BuilderSupport implements Builder {

        private final CartItem item;

        public BuilderSupport(CartItem item) {
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

        public Builder check() {
            this.item.check();
            return this;
        }

        public Builder uncheck() {
            this.item.uncheck();
            return this;
        }

        public Builder checked(boolean checked) {
            return checked
                    ? this.check()
                    : this.uncheck();
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
