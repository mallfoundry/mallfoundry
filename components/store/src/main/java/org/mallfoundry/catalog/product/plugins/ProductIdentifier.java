package org.mallfoundry.catalog.product.plugins;

import org.mallfoundry.catalog.product.Product;
import org.mallfoundry.catalog.product.ProductOption;
import org.mallfoundry.catalog.product.ProductProcessor;
import org.mallfoundry.keygen.PrimaryKeyHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 用于设置商品对象标识插件。如果商品对象标识不为空则不会设置一个新的标识值。
 *
 * @author Zhi Tang
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Service
public class ProductIdentifier implements ProductProcessor {

    /**
     * 商品对象标识值名称。
     */
    private static final String PRODUCT_ID_VALUE_NAME = "catalog.product.id";

    /**
     * 商品变体对象标识值名称。
     */
    private static final String PRODUCT_VARIANT_ID_VALUE_NAME = "store.product.variant.id";

    /**
     * 商品选项对象标识值名称。
     */
    private static final String PRODUCT_OPTION_ID_VALUE_NAME = "catalog.product.option.id";

    /**
     * 商品选项值对象标识值名称。
     */
    private static final String PRODUCT_OPTION_VALUE_ID_VALUE_NAME = "catalog.product.option.value.id";

    @Override
    public Product processPreAddProduct(Product product) {
        this.setProduct(product);
        return product;
    }

    private void setProduct(Product product) {
        if (Objects.isNull(product.getId())) {
            product.setId(PrimaryKeyHolder.next(PRODUCT_ID_VALUE_NAME));
        }
        setProductOptions(product.getOptions());
        setProductVariants(product);
    }

    private void setProductOptions(List<ProductOption> options) {
        options.stream()
                .peek(option -> {
                    if (Objects.isNull(option.getId())) {
                        option.setId(PrimaryKeyHolder.next(PRODUCT_OPTION_ID_VALUE_NAME));
                    }
                })
                // 设置商品选项值对象的标识。
                .flatMap(option -> option.getValues().stream())
                .forEach(value -> {
                    if (Objects.isNull(value.getId())) {
                        value.setId(PrimaryKeyHolder.next(PRODUCT_OPTION_VALUE_ID_VALUE_NAME));
                    }
                });
    }

    private void setProductVariants(Product product) {
        product.getVariants().stream()
                .peek(variant -> {
                    if (Objects.isNull(variant.getId())) {
                        variant.setId(PrimaryKeyHolder.next(PRODUCT_VARIANT_ID_VALUE_NAME));
                    }
                })
                // 对商品变体对象所关联的选项对象和选项值对象设置相关联标识值。
                .flatMap(variant -> variant.getOptionSelections().stream())
                .forEach(selection -> {
                    // 如果选择的商品选项对象的名称标识或者值标识为空时，才对其执行设置标识。
                    // 如果商标变体对象所关联的商品选项对象不存在，则忽略设置。
                    if (Objects.isNull(selection.getNameId()) || Objects.isNull(selection.getValueId())) {
                        product.selectOption(selection.getName(), selection.getValue())
                                .ifPresent(aSelection -> {
                                    if (Objects.isNull(selection.getNameId())) {
                                        selection.setNameId(aSelection.getNameId());
                                    }
                                    if (Objects.isNull(selection.getValueId())) {
                                        selection.setValueId(aSelection.getValueId());
                                    }
                                });
                    }
                });
    }
}
