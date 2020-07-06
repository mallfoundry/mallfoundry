package org.mallfoundry.catalog.product;

import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.inventory.InventoryException;
import org.mallfoundry.inventory.InventoryStatus;
import org.mallfoundry.util.ObjectBuilder;
import org.mallfoundry.util.Position;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface ProductVariant extends Serializable, Position {

    String getId();

    void setId(String id);

    String getStoreId();

    void setStoreId(String storeId);

    String getProductId();

    void setProductId(String productId);

    BigDecimal getWeight();

    void setWeight(BigDecimal weight);

    BigDecimal getWidth();

    void setWidth(BigDecimal width);

    BigDecimal getHeight();

    void setHeight(BigDecimal height);

    BigDecimal getDepth();

    void setDepth(BigDecimal depth);

    String getBarcode();

    void setBarcode(String barcode);

    String getSku();

    void setSku(String sku);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    BigDecimal getSalePrice();

    void setSalePrice(BigDecimal salePrice);

    BigDecimal getRetailPrice();

    void setRetailPrice(BigDecimal retailPrice);

    BigDecimal getCostPrice();

    void setCostPrice(BigDecimal costPrice);

    List<OptionSelection> getOptionSelections();

    void setOptionSelections(List<OptionSelection> optionSelections);

    List<String> getImageUrls();

    void setImageUrls(List<String> imageUrls);

    int getInventoryQuantity();

    void adjustInventoryQuantity(int quantityDelta) throws InventoryException;

    InventoryStatus getInventoryStatus();

    Builder toBuilder();

    interface Builder extends ObjectBuilder<ProductVariant> {

        Builder price(double price);

        Builder retailPrice(double retailPrice);

        Builder adjustInventoryQuantity(int quantityDelta);

        Builder position(int position);

        Builder optionSelections(List<OptionSelection> optionSelections);

        Builder imageUrls(List<String> imageUrls);
    }
}
