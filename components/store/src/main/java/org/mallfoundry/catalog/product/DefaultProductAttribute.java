package org.mallfoundry.catalog.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
public class DefaultProductAttribute extends ProductAttributeSupport {

    private String namespace;

    private String name;

    private String value;

    private int position;

    public DefaultProductAttribute(String name, String value) {
        super(name, value);
    }

    public DefaultProductAttribute(String namespace, String name, String value) {
        super(namespace, name, value);
    }

    public static DefaultProductAttribute of(ProductAttribute attribute) {
        if (attribute instanceof DefaultProductAttribute) {
            return (DefaultProductAttribute) attribute;
        }
        var target = new DefaultProductAttribute();
        BeanUtils.copyProperties(attribute, target);
        return target;
    }
}
