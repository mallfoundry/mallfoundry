package org.mallfoundry.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mallfoundry.catalog.OptionSelection;
import org.mallfoundry.catalog.product.repository.jpa.convert.OptionSelectionListConverter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_cart_item")
public class InternalCartItem implements CartItem {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "store_id_")
    private String storeId;

    @Column(name = "product_id_")
    private String productId;

    @Column(name = "variant_id_")
    private String variantId;

    @Column(name = "name_")
    private String name;

    @Column(name = "image_url_")
    private String imageUrl;

    @Column(name = "option_selections_", length = 1024)
    @Convert(converter = OptionSelectionListConverter.class)
    private List<OptionSelection> optionSelections;

    @Column(name = "price_")
    private BigDecimal price;

    @Column(name = "quantity_")
    private int quantity;

    @Column(name = "checked_")
    private boolean checked;

    @Column(name = "added_time_")
    private Date addedTime;

    public InternalCartItem(String id) {
        this.id = id;
        this.addedTime = new Date();
    }

    public static InternalCartItem of(CartItem item) {
        if (item instanceof InternalCartItem) {
            return (InternalCartItem) item;
        }
        var target = new InternalCartItem();
        BeanUtils.copyProperties(item, target);
        return target;
    }

    @Override
    public void incrementQuantity(int quantity) {
        this.setQuantity(this.quantity + quantity);
    }

    @Override
    public void check() {
        this.checked = true;
    }

    @Override
    public void uncheck() {
        this.checked = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InternalCartItem that = (InternalCartItem) o;
        return Objects.equals(productId, that.productId)
                && Objects.equals(variantId, that.variantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, variantId);
    }


}
