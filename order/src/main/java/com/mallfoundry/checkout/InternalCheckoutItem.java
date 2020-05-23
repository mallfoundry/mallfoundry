package com.mallfoundry.checkout;

import com.mallfoundry.data.jpa.convert.StringListConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "checkout_items")
public class InternalCheckoutItem implements CheckoutItem {

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

}
