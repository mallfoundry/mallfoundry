package com.mallfoundry.checkout;

import com.mallfoundry.order.InternalOrder;
import com.mallfoundry.order.Order;
import com.mallfoundry.order.ShippingAddress;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "checkouts")
public class InternalCheckout implements Checkout {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "shipping_address_")
    private ShippingAddress shippingAddress;

    @OneToMany(targetEntity = InternalCheckoutItem.class)
    @JoinColumn(name = "checkout_id_")
    private List<CheckoutItem> items;

    @ManyToMany(targetEntity = InternalOrder.class)
    @JoinTable(name = "checkout_orders",
            joinColumns = @JoinColumn(name = "checkout_id_"),
            inverseJoinColumns = @JoinColumn(name = "order_id_"))
    private List<Order> orders;

}
