package org.mallfoundry.catalog.product.repository.elasticsearch;

import lombok.Getter;
import org.mallfoundry.catalog.product.DefaultProductShippingOrigin;
import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductAttribute;
import org.mallfoundry.catalog.product.ProductOption;
import org.mallfoundry.catalog.product.ProductShippingOrigin;
import org.mallfoundry.catalog.product.ProductStatus;
import org.mallfoundry.catalog.product.ProductSupport;
import org.mallfoundry.catalog.product.ProductType;
import org.mallfoundry.catalog.product.ProductVariant;
import org.mallfoundry.inventory.InventoryStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.util.CastUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Getter
@Document(indexName = "mf_catalog_product")
public class ElasticsearchProduct extends ProductSupport {

    @Id
    private String id;

    private String storeId;

    private ProductType type;

    private ProductStatus status;

    @Field(type = FieldType.Text)
    private String name;

    private String description;

    private BigDecimal price;

    private String categoryId;

    private String brandId;

    private Set<String> collections = new HashSet<>();

    private long totalSales;

    private long monthlySales;

    private BigDecimal marketPrice;

    private int inventoryQuantity;

    private InventoryStatus inventoryStatus;

    private List<ElasticsearchProductOption> options = new ArrayList<>();

    @Field(type = FieldType.Nested)
    private List<ElasticsearchProductAttribute> attributes = new ArrayList<>();

    private List<ElasticsearchProductVariant> variants = new ArrayList<>();

    private List<String> imageUrls = new ArrayList<>();

    private List<String> videoUrls = new ArrayList<>();

    private DefaultProductShippingOrigin shippingOrigin;

    private boolean freeShipping;

    private BigDecimal fixedShippingCost;

    private String shippingRateId;

    private Date createdTime;

    private long version;

    public ElasticsearchProduct(String id) {
        super(id);
    }


    @Override
    protected void doSetId(String id) {
        this.id = id;
    }

    @Override
    protected void doSetName(String name) {
        this.name = name;
    }

    @Override
    protected void doSetFreeShipping(boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    @Override
    protected void doSetInventoryStatus(InventoryStatus status) {
        this.inventoryStatus = status;
    }

    @Override
    protected void doSetOptions(List<ProductOption> options) {
        this.attributes = Objects.requireNonNullElseGet(attributes, (Supplier<List<ProductAttribute>>) ArrayList::new)
                .stream().map(ElasticsearchProductAttribute::of).collect(Collectors.toList());
    }

    @Override
    protected void doSetVariants(List<ProductVariant> variants) {
        this.variants = Objects.requireNonNullElseGet(variants, (Supplier<List<ProductVariant>>) ArrayList::new)
                .stream().map(ElasticsearchProductVariant::of).collect(Collectors.toList());
    }

    @Override
    protected void doSetCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    protected void doSetStoreId(String storeId) {
        this.storeId = storeId;
    }

    @Override
    protected void doSetType(ProductType type) {
        this.type = type;
    }

    @Override
    protected void doSetStatus(ProductStatus status) {
        this.status = status;
    }

    @Override
    protected void doSetDescription(String description) {
        this.description = description;
    }

    @Override
    protected void doSetCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    protected void doSetBrandId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    protected void doSetCollections(Set<String> collections) {
        this.collections = collections;
    }

    @Override
    protected void doSetShippingOrigin(ProductShippingOrigin shippingOrigin) {
        this.shippingOrigin = DefaultProductShippingOrigin.of(shippingOrigin);
    }

    @Override
    protected void doSetFixedShippingCost(BigDecimal fixedShippingCost) {
        this.fixedShippingCost = fixedShippingCost;
    }

    @Override
    protected void doSetShippingRateId(String shippingRateId) {
        this.shippingRateId = shippingRateId;
    }

    @Override
    protected void doSetAttributes(List<ProductAttribute> attributes) {
        this.attributes = Objects.requireNonNullElseGet(attributes, (Supplier<List<ProductAttribute>>) ArrayList::new)
                .stream().map(ElasticsearchProductAttribute::of).collect(Collectors.toList());
    }

    @Override
    protected void doSetTotalSales(long totalSales) {
        this.totalSales = totalSales;
    }

    @Override
    protected void doSetMonthlySales(long monthlySales) {
        this.monthlySales = monthlySales;
    }

    @Override
    protected void doSetImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    protected void doSetVideoUrls(List<String> videoUrls) {
        this.videoUrls = videoUrls;
    }

    @Override
    protected void doSetVersion(long version) {
        this.version = version;
    }

    public static ElasticsearchProduct of(Product product) {
        if (product instanceof ElasticsearchProduct) {
            return (ElasticsearchProduct) product;
        }
        var target = new ElasticsearchProduct(product.getId());
        BeanUtils.copyProperties(product, target);
        return target;
    }

    @Override
    public ProductVariant createVariant(String id) {
        return new ElasticsearchProductVariant(id);
    }

    @Override
    public ProductOption createOption(String id) {
        return new ElasticsearchProductOption(id);
    }

    @Override
    public ProductAttribute createAttribute(String name, String value) {
        return new ElasticsearchProductAttribute(name, value);
    }

    @Override
    public ProductAttribute createAttribute(String namespace, String name, String value) {
        return new ElasticsearchProductAttribute(namespace, name, value);
    }

    @Override
    public List<ProductVariant> getVariants() {
        return CastUtils.cast(this.variants);
    }

    @Override
    public List<ProductOption> getOptions() {
        return CastUtils.cast(options);
    }

    @Override
    public List<ProductAttribute> getAttributes() {
        return CastUtils.cast(this.attributes);
    }
}
