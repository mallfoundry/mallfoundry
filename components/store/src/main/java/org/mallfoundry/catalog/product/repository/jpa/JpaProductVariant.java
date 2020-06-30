package org.mallfoundry.catalog.product.repository.jpa;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.catalog.DefaultOptionSelection;
import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.catalog.product.ProductVariantSupport;
import org.mallfoundry.catalog.product.repository.jpa.convert.OptionSelectionListConverter;
import org.mallfoundry.data.jpa.convert.StringListConverter;
import org.mallfoundry.inventory.InventoryStatus;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_catalog_product_variant")
public class JpaProductVariant extends ProductVariantSupport {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "product_id_")
    private String productId;

    @Column(name = "store_id_")
    private String storeId;

    @Column(name = "weight_")
    private BigDecimal weight;

    @Column(name = "width_")
    private BigDecimal width;

    @Column(name = "height_")
    private BigDecimal height;

    @Column(name = "depth_")
    private BigDecimal depth;

    @Column(name = "barcode_")
    private String barcode;

    @Column(name = "sku_")
    private String sku;

    @Column(name = "price_")
    private BigDecimal price;

    @Column(name = "sale_price_")
    private BigDecimal salePrice;

    @Column(name = "retail_price_")
    private BigDecimal retailPrice;

    @Column(name = "cost_price_")
    private BigDecimal costPrice;

    @Column(name = "inventory_quantity_")
    private int inventoryQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "inventory_status_")
    private InventoryStatus inventoryStatus;

    @Column(name = "option_selections_")
    @Convert(converter = OptionSelectionListConverter.class)
    @JsonDeserialize(contentAs = DefaultOptionSelection.class)
    private List<OptionSelection> optionSelections = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column(name = "image_urls_", length = 521)
    private List<String> imageUrls;

    @Column(name = "position_")
    private int position;

    public JpaProductVariant(String id) {
        super(id);
    }
}
