package com.mallfoundry.cart;

import com.mallfoundry.cart.CartItem;
import com.mallfoundry.data.jpa.convert.StringListConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cart_items")
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

    @Column(name = "option_values_")
    @Convert(converter = StringListConverter.class)
    private List<String> optionValues;

    @Column(name = "quantity_")
    private int quantity;

    @Column(name = "added_time_")
    private Date addedTime;

    public InternalCartItem(String id) {
        this.id = id;
        this.addedTime = new Date();
    }
}
