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

package org.mallfoundry.catalog.product.repository.jpa;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.catalog.product.DefaultProductAttribute;
import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductAttribute;
import org.mallfoundry.catalog.product.ProductOption;
import org.mallfoundry.catalog.product.ProductOrigin;
import org.mallfoundry.catalog.product.ProductStatus;
import org.mallfoundry.catalog.product.ProductSupport;
import org.mallfoundry.catalog.product.ProductType;
import org.mallfoundry.catalog.product.ProductVariant;
import org.mallfoundry.catalog.product.repository.jpa.convert.ProductAttributeListConverter;
import org.mallfoundry.catalog.product.repository.jpa.convert.ProductOriginConverter;
import org.mallfoundry.data.repository.jpa.convert.StringListConverter;
import org.mallfoundry.inventory.InventoryStatus;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_catalog_product")
public class JpaProduct extends ProductSupport {

    @NotBlank
    @Id
    @Column(name = "id_")
    private String id;

    @NotBlank
    @Column(name = "store_id_")
    private String storeId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type_")
    private ProductType type;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status_")
    private ProductStatus status;

    @NotBlank
    @Column(name = "name_")
    private String name;

    @Column(name = "description_")
    private String description;

    @NotBlank
    @Column(name = "category_id_")
    private String categoryId;

    @NotBlank
    @Column(name = "brand_id_")
    private String brandId;

    @ElementCollection
    @CollectionTable(name = "mf_catalog_product_collection", joinColumns = @JoinColumn(name = "product_id_"))
    @Column(name = "collection_id_")
    private Set<String> collections = new HashSet<>();

    @Column(name = "total_sales_")
    private long totalSales;

    @Column(name = "monthly_sales_")
    private long monthlySales;

    @Column(name = "price_")
    private BigDecimal price;

    @Column(name = "inventory_quantity_")
    private int inventoryQuantity;

    @Column(name = "inventory_status_")
    private InventoryStatus inventoryStatus;

    @Valid
    @OneToMany(targetEntity = JpaProductOption.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id_")
    @OrderBy("position")
    @JsonDeserialize(contentAs = JpaProductOption.class)
    private List<ProductOption> options = new ArrayList<>();

    @Column(name = "attributes_", length = 2048)
    @Convert(converter = ProductAttributeListConverter.class)
    @JsonDeserialize(contentAs = DefaultProductAttribute.class)
    private List<ProductAttribute> attributes = new ArrayList<>();

    @Valid
    @OneToMany(targetEntity = JpaProductVariant.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id_")
    @OrderBy("position")
    @JsonDeserialize(contentAs = JpaProductVariant.class)
    private List<ProductVariant> variants = new ArrayList<>();

    @NotEmpty
    @Column(name = "image_urls_", length = 2048)
    @Convert(converter = StringListConverter.class)
    private List<String> imageUrls = new ArrayList<>();

    @Column(name = "video_urls_", length = 2048)
    @Convert(converter = StringListConverter.class)
    private List<String> videoUrls = new ArrayList<>();

    @NotNull
    @Column(name = "origin_", length = 512)
    @Convert(converter = ProductOriginConverter.class)
    private ProductOrigin origin;

    @Column(name = "free_shipping_")
    private boolean freeShipping;

    @Column(name = "fixed_shipping_cost_")
    private BigDecimal fixedShippingCost;

    @Column(name = "shipping_rate_id_")
    private String shippingRateId;

    @Column(name = "published_time_")
    private Date publishedTime;

    @NotNull
    @Column(name = "created_time_")
    private Date createdTime;

    public JpaProduct(String id) {
        super(id);
    }

    public static JpaProduct of(Product product) {
        if (product instanceof JpaProduct) {
            return (JpaProduct) product;
        }
        var target = new JpaProduct(product.getId());
        BeanUtils.copyProperties(product, target);
        return target;
    }

    @Override
    public ProductVariant createVariant(String id) {
        return new JpaProductVariant(id);
    }

    @Override
    public ProductOption createOption(String id) {
        return new JpaProductOption(id);
    }

    @Override
    public ProductAttribute createAttribute() {
        return new DefaultProductAttribute();
    }

    @Override
    public ProductAttribute createAttribute(String name, String value) {
        return new DefaultProductAttribute(name, value);
    }

    @Override
    public ProductAttribute createAttribute(String namespace, String name, String value) {
        return new DefaultProductAttribute(namespace, name, value);
    }

    public void setFixedShippingCost(BigDecimal fixedShippingCost) {
        super.setFixedShippingCost(fixedShippingCost);
        this.fixedShippingCost = fixedShippingCost;
        this.shippingRateId = null;
    }

    public void setShippingRateId(String shippingRateId) {
        super.setShippingRateId(shippingRateId);
        this.shippingRateId = shippingRateId;
        this.fixedShippingCost = null;
    }
}
