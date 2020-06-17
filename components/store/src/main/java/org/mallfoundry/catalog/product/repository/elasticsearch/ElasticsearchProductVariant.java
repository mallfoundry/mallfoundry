package org.mallfoundry.catalog.product.repository.elasticsearch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.catalog.product.ProductVariant;
import org.mallfoundry.catalog.product.ProductVariantSupport;
import org.mallfoundry.inventory.InventoryException;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ElasticsearchProductVariant extends ProductVariantSupport {

    private String id;

    private String productId;

    private String storeId;

    private BigDecimal price;

    private BigDecimal marketPrice;

    private BigDecimal costPrice;

    private String weight;

    private int inventoryQuantity;

    private List<OptionSelection> optionSelections = new ArrayList<>();

    private List<String> imageUrls;

    private int position;

    public ElasticsearchProductVariant(String id) {
        super(id);
    }

    public static ElasticsearchProductVariant of(ProductVariant variant) {
        if (variant instanceof ElasticsearchProductVariant) {
            return (ElasticsearchProductVariant) variant;
        }
        var target = new ElasticsearchProductVariant();
        BeanUtils.copyProperties(variant, target);
        return target;
    }

    @Override
    protected void doSetInventoryQuantity(int quantity) throws InventoryException {
        this.inventoryQuantity = quantity;
    }
}
