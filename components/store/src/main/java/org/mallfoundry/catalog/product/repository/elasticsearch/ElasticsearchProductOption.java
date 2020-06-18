package org.mallfoundry.catalog.product.repository.elasticsearch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.catalog.product.ProductOption;
import org.mallfoundry.catalog.product.ProductOptionSupport;
import org.mallfoundry.catalog.product.ProductOptionValue;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ElasticsearchProductOption extends ProductOptionSupport {

    private String id;

    private String name;

    private List<ProductOptionValue> values = new ArrayList<>();

    private int position;

    public ElasticsearchProductOption(String id) {
        super(id);
    }

    public static ElasticsearchProductOption of(ProductOption option) {
        if (option instanceof ElasticsearchProductOption) {
            return (ElasticsearchProductOption) option;
        }
        var target = new ElasticsearchProductOption();
        BeanUtils.copyProperties(option, target);
        return target;
    }
}
