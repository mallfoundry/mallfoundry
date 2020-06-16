package org.mallfoundry.catalog.product.repository.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.catalog.product.InternalProductAttribute;
import org.mallfoundry.catalog.product.InternalProductOption;
import org.mallfoundry.catalog.product.InternalProductVariant;
import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductAttribute;
import org.mallfoundry.catalog.product.ProductOption;
import org.mallfoundry.catalog.product.ProductStatus;
import org.mallfoundry.catalog.product.ProductSupport;
import org.mallfoundry.catalog.product.ProductType;
import org.mallfoundry.catalog.product.ProductVariant;
import org.mallfoundry.catalog.product.repository.jpa.convert.ProductAttributeListConverter;
import org.mallfoundry.data.jpa.convert.StringListConverter;
import org.mallfoundry.data.jpa.convert.StringSetConverter;
import org.mallfoundry.inventory.InventoryStatus;
import org.springframework.beans.BeanUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "mf_catalog_product")
public class JpaProduct extends ProductSupport {

    public static JpaProduct of(Product product) {
        if (product instanceof JpaProduct) {
            return (JpaProduct) product;
        }
        var target = new JpaProduct(product.getId());
        BeanUtils.copyProperties(product, target);
        return target;
    }

    public JpaProduct() {
        super(null);
    }

    public JpaProduct(String id) {
        super(id);
    }

    @Id
    @Column(name = "id_")
    @Override
    public String getId() {
        return super.getId();
    }

    @Column(name = "store_id_")
    @Override
    public String getStoreId() {
        return super.getStoreId();
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type_")
    @Override
    public ProductType getType() {
        return super.getType();
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status_")
    @Override
    public ProductStatus getStatus() {
        return super.getStatus();
    }

    @Column(name = "name_")
    @Override
    public String getName() {
        return super.getName();
    }

    @Column(name = "description_")
    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Column(name = "collections_")
    @Convert(converter = StringSetConverter.class)
    @Override
    public Set<String> getCollections() {
        return super.getCollections();
    }

    @Column(name = "market_price_")
    @Override
    public BigDecimal getMarketPrice() {
        return super.getMarketPrice();
    }

    @OneToMany(targetEntity = InternalProductOption.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id_")
    @JsonDeserialize(contentAs = InternalProductOption.class)
    @Override
    public List<ProductOption> getOptions() {
        return super.getOptions();
    }

    @Column(name = "attributes_", length = 2048)
    @Convert(converter = ProductAttributeListConverter.class)
    @JsonDeserialize(contentAs = InternalProductAttribute.class)
    @Override
    public List<ProductAttribute> getAttributes() {
        return super.getAttributes();
    }

    @OneToMany(targetEntity = InternalProductVariant.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonDeserialize(contentAs = InternalProductVariant.class)
    @JoinColumn(name = "product_id_")
    @Override
    public List<ProductVariant> getVariants() {
        return super.getVariants();
    }

    @Column(name = "image_urls_", length = 2048)
    @Convert(converter = StringListConverter.class)
    @Override
    public List<String> getImageUrls() {
        return super.getImageUrls();
    }

    @Column(name = "video_urls_", length = 2048)
    @Convert(converter = StringListConverter.class)
    @Override
    public List<String> getVideoUrls() {
        return super.getVideoUrls();
    }

    @Column(name = "shipping_origin_")
    @Override
    public String getShippingOrigin() {
        return super.getShippingOrigin();
    }

    @Column(name = "free_shipping_")
    @Override
    public boolean isFreeShipping() {
        return super.isFreeShipping();
    }

    @Column(name = "fixed_shipping_cost_")
    @Override
    public BigDecimal getFixedShippingCost() {
        return super.getFixedShippingCost();
    }

    @Column(name = "shipping_rate_id_")
    @Override
    public String getShippingRateId() {
        return super.getShippingRateId();
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_time_")
    @Override
    public Date getCreatedTime() {
        return super.getCreatedTime();
    }

    @Transient
    @Override
    public BigDecimal getPrice() {
        return super.getPrice();
    }

    @Transient
    @Override
    public int getInventoryQuantity() {
        return super.getInventoryQuantity();
    }

    @Transient
    @Override
    public InventoryStatus getInventoryStatus() {
        return super.getInventoryStatus();
    }

    @Override
    public void addVariant(ProductVariant variant) {
        super.addVariant(variant);
    }

    @Override
    public void adjustVariantInventoryQuantity(String variantId, int quantityDelta) {
        super.adjustVariantInventoryQuantity(variantId, quantityDelta);
    }

    @Override
    public Optional<ProductVariant> getVariant(String id) {
        return super.getVariant(id);
    }

    @Override
    public ProductVariant createVariant(String id) {
        return super.createVariant(id);
    }

    @Override
    public ProductOption createOption(String id) {
        return super.createOption(id);
    }

    @Override
    public Optional<ProductOption> getOption(String name) {
        return super.getOption(name);
    }

    @Override
    public void addOption(ProductOption option) {
        super.addOption(option);
    }

    @Override
    public Optional<OptionSelection> selectOption(String name, String label) {
        return super.selectOption(name, label);
    }

    @Override
    public ProductAttribute createAttribute(String name, String value) {
        return super.createAttribute(name, value);
    }

    @Override
    public ProductAttribute createAttribute(String namespace, String name, String value) {
        return super.createAttribute(namespace, name, value);
    }

    @Override
    public Optional<ProductAttribute> getAttribute(String namespace, String name) {
        return super.getAttribute(namespace, name);
    }

    @Override
    public void addImageUrl(String imageUrl) {
        super.addImageUrl(imageUrl);
    }

    @Override
    public void addVideoUrl(String video) {
        super.addVideoUrl(video);
    }

    @Override
    public void addAttribute(ProductAttribute attribute) {
        super.addAttribute(attribute);
    }

    @Override
    public void create() {
        super.create();
    }
}
