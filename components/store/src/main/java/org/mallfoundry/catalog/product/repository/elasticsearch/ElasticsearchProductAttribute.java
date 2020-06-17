package org.mallfoundry.catalog.product.repository.elasticsearch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.catalog.product.ProductAttribute;
import org.mallfoundry.catalog.product.ProductAttributeSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@NoArgsConstructor
public class ElasticsearchProductAttribute extends ProductAttributeSupport {

    private String namespace;

    private String name;

    @Field(type = FieldType.Keyword)
    private String value;

    private int position;

    public ElasticsearchProductAttribute(String name, String value) {
        super(name, value);
    }

    public ElasticsearchProductAttribute(String namespace, String name, String value) {
        super(namespace, name, value);
    }

    public static ElasticsearchProductAttribute of(ProductAttribute attribute) {
        if (attribute instanceof ElasticsearchProductAttribute) {
            return (ElasticsearchProductAttribute) attribute;
        }
        var target = new ElasticsearchProductAttribute();
        BeanUtils.copyProperties(attribute, target);
        return target;
    }
}
