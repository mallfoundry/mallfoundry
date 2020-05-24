/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mallfoundry.catalog.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mallfoundry.catalog.product.repository.jpa.convert.OptionSelectionListConverter;
import com.mallfoundry.data.jpa.convert.StringListConverter;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Each product can have multiple variations.
 * The product variant is a combination of option values.
 *
 * @author Zhi Tang
 */
@Getter
@Setter
@Entity
@Table(name = "catalog_product_variants")
public class InternalProductVariant implements ProductVariant {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "price_")
    private BigDecimal price;

    @Column(name = "market_price_")
    private BigDecimal marketPrice;

    @Column(name = "cost_price_")
    private BigDecimal costPrice;

    @Column(name = "weight_")
    private String weight;

    @Column(name = "inventory_quantity_")
    private int inventoryQuantity;

    @Column(name = "option_selections_")
    @Convert(converter = OptionSelectionListConverter.class)
    private List<OptionSelection> optionSelections = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column(name = "image_urls_", length = 521)
    private List<String> imageUrls;

    @Column(name = "position_")
    private Integer position;

    @JsonIgnore
    @Override
    public String getFirstImageUrl() {
        return CollectionUtils.isEmpty(imageUrls) ? null : imageUrls.iterator().next();
    }

    @Override
    public void setInventoryQuantity(int quantity) {
        if (quantity < 0) {
            throw new RuntimeException("");
        }
        this.inventoryQuantity = quantity;
    }

    @Override
    public void adjustInventoryQuantity(int adjustQuantity) {
        this.setInventoryQuantity(this.inventoryQuantity + adjustQuantity);
    }

    @Override
    public InventoryStatus getInventoryStatus() {
        return this.getInventoryQuantity() == 0 ? InventoryStatus.OUT_OF_STOCK : InventoryStatus.IN_STOCK;
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InternalProductVariant that = (InternalProductVariant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
