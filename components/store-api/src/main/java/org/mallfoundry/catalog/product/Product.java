package org.mallfoundry.catalog.product;

import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.inventory.InventoryStatus;
import org.mallfoundry.util.ObjectBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public interface Product extends Serializable {

    String getId();

    void setId(String id);

    String getStoreId();

    void setStoreId(String storeId);

    String getName();

    void setName(String name);

    ProductType getType();

    void setType(ProductType type);

    ProductStatus getStatus();

    void setStatus(ProductStatus status);

    String getDescription();

    void setDescription(String description);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    BigDecimal getMarketPrice();

    void setMarketPrice(BigDecimal marketPrice);

    String getCategoryId();

    void setCategoryId(String categoryId);

    String getBrandId();

    void setBrandId(String brandId);

    Set<String> getCollections();

    void setCollections(Set<String> collections);

    long getTotalSales();

    void adjustTotalSales(long sales);

    long getMonthlySales();

    void adjustMonthlySales(long sales);

    List<ProductOption> getOptions();

    List<String> getImageUrls();

    List<String> getVideoUrls();

    ProductShippingOrigin createShippingOrigin();

    ProductShippingOrigin getShippingOrigin();

    void setShippingOrigin(ProductShippingOrigin shippingOrigin);

    boolean isFreeShipping();

    void setFreeShipping(boolean freeShipping);

    void freeShipping();

    BigDecimal getFixedShippingCost();

    void setFixedShippingCost(BigDecimal fixedShippingCost);

    String getShippingRateId();

    void setShippingRateId(String shippingRateId);

    int getInventoryQuantity();

    InventoryStatus getInventoryStatus();

    List<ProductVariant> getVariants();

    Date getCreatedTime();

    Optional<ProductVariant> getVariant(String variantId);

    ProductVariant createVariant(String id);

    void addVariant(ProductVariant variant);

    void adjustInventoryQuantity(String variantId, int adjustQuantity);

    ProductOption createOption(String id);

    Optional<ProductOption> getOption(String name);

    void addOption(ProductOption option);

    Optional<OptionSelection> selectOption(String name, String label);

    ProductAttribute createAttribute(String name, String value);

    ProductAttribute createAttribute(String namespace, String name, String value);

    Optional<ProductAttribute> getAttribute(String namespace, String name);

    void addAttribute(ProductAttribute attribute);

    List<ProductAttribute> getAttributes();

    void setAttributes(List<ProductAttribute> attributes);

    void addImageUrl(String url);

    void addVideoUrl(String url);

    void create();

    Builder toBuilder();

    interface Builder extends ObjectBuilder<Product> {

        Builder storeId(String storeId);

        Builder name(String name);

        Builder type(ProductType type);

        Builder status(ProductStatus status);

        Builder freeShipping();

        Builder fixedShippingCost(BigDecimal fixedShippingCost);

        Builder fixedShippingCost(double fixedShippingCost);

        Builder shippingOrigin(ProductShippingOrigin shippingOrigin);

        Builder shippingOrigin(Function<Product, ProductShippingOrigin> shippingOrigin);

        Builder adjustTotalSales(long sales);

        Builder adjustMonthlySales(long sales);

        Builder collections(Set<String> collections);

        Builder imageUrl(String image);

        Builder videoUrl(String video);

        Builder option(ProductOption option);

        Builder option(Function<Product, ProductOption> option);

        Builder variant(ProductVariant variant);

        Builder variant(Function<Product, ProductVariant> variant);

        Builder attribute(ProductAttribute attribute);

        Builder attribute(Function<Product, ProductAttribute> attribute);

        Builder create();
    }
}
