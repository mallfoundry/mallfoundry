package org.mallfoundry.catalog.product.repository.elasticsearch;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductAttribute;
import org.mallfoundry.catalog.product.ProductOption;
import org.mallfoundry.catalog.product.ProductStatus;
import org.mallfoundry.catalog.product.ProductSupport;
import org.mallfoundry.catalog.product.ProductType;
import org.mallfoundry.catalog.product.ProductVariant;
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
import java.util.stream.Collectors;

@Getter
@Setter
@Document(indexName = "mf_product")
public class ElasticsearchProduct extends ProductSupport {

    @Id
    private String id;

    private String storeId;

    private ProductType type;

    private ProductStatus status;

    private String name;

    private String description;

    private BigDecimal price;

    private Set<String> collections = new HashSet<>();

    private BigDecimal marketPrice;

    private List<ProductOption> options = new ArrayList<>();

    @Field(type = FieldType.Nested)
    private List<ElasticsearchProductAttribute> attributes = new ArrayList<>();

    private List<ElasticsearchProductVariant> variants = new ArrayList<>();

    private List<String> imageUrls = new ArrayList<>();

    private List<String> videoUrls = new ArrayList<>();

    private String shippingOrigin;

    private boolean freeShipping;

    private BigDecimal fixedShippingCost;

    private String shippingRateId;

    private Date createdTime;

    public ElasticsearchProduct(String id) {
        super(id);
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
    public ProductAttribute createAttribute(String name, String value) {
        return new ElasticsearchProductAttribute(name, value);
    }

    @Override
    public ProductAttribute createAttribute(String namespace, String name, String value) {
        return new ElasticsearchProductAttribute(namespace, name, value);
    }

    @Override
    public List<ProductVariant> getVariants() {
        return CastUtils.cast(variants);
    }

    @Override
    public void setVariants(List<ProductVariant> variants) {
        if (Objects.nonNull(variants)) {
            this.variants = variants.stream().map(ElasticsearchProductVariant::of).collect(Collectors.toList());
        }
    }

    @Override
    public List<ProductAttribute> getAttributes() {
        return CastUtils.cast(attributes);
    }

    @Override
    public void setAttributes(List<ProductAttribute> attributes) {
        if (Objects.nonNull(attributes)) {
            this.attributes = attributes.stream().map(ElasticsearchProductAttribute::of).collect(Collectors.toList());
        }
    }

}
