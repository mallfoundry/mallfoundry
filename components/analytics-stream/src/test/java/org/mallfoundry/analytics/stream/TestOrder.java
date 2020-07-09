package org.mallfoundry.analytics.stream;

import lombok.Getter;
import lombok.Setter;
import org.mallfoundry.order.Order;
import org.mallfoundry.order.OrderException;
import org.mallfoundry.order.OrderItem;
import org.mallfoundry.order.OrderStatus;
import org.mallfoundry.order.PaymentDetails;
import org.mallfoundry.order.Refund;
import org.mallfoundry.order.Shipment;
import org.mallfoundry.shipping.Address;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class TestOrder implements Order {
    private String id;
    private OrderStatus status;
    private String storeId;
    private String storeName;
    private String customerId;
    private String customerMessage;
    private String staffNotes;
    private Integer staffStars;
    private Address shippingAddress;
    private List<OrderItem> items = new ArrayList<>();
    private List<Shipment> shipments = new ArrayList<>();
    private List<Refund> refunds = new ArrayList<>();
    private int shippedItems;
    private PaymentDetails paymentDetails;
    private int paymentExpires = 60 * 1000;
    private String cancelReason;
    private Date createdTime;
    private Date placedTime;
    private Date paidTime;
    private Date shippedTime;
    private Date fulfilledTime;
    private Date signedTime;
    private Date receivedTime;
    private Date cancelledTime;

    @Override
    public OrderItem createItem(String itemId) {
        return null;
    }

    @Override
    public void addItem(OrderItem item) {

    }

    @Override
    public Optional<OrderItem> getItem(String itemId) {
        return Optional.empty();
    }

    @Override
    public List<OrderItem> getItems(List<String> itemIds) {
        return null;
    }

    @Override
    public Shipment createShipment(String shipmentId) {
        return null;
    }

    @Override
    public void addShipment(Shipment shipment) {

    }

    @Override
    public Optional<Shipment> getShipment(String id) {
        return Optional.empty();
    }

    @Override
    public void setShipment(Shipment shipment) {

    }

    @Override
    public void removeShipment(Shipment shipment) {

    }

    @Override
    public void addRefund(Refund refund) {

    }

    @Override
    public int getTotalItems() {
        return 0;
    }

    @Override
    public BigDecimal getTotalDiscountAmount() {
        return null;
    }

    @Override
    public BigDecimal getTotalShippingCost() {
        return null;
    }

    @Override
    public BigDecimal getTotalDiscountShippingCost() {
        return null;
    }

    @Override
    public BigDecimal getTotalPrice() {
        return null;
    }

    @Override
    public BigDecimal getTotalAmount() {
        return null;
    }

    @Override
    public BigDecimal getSubtotalAmount() {
        return null;
    }

    @Override
    public void discounts(Map<String, BigDecimal> amounts) {

    }

    @Override
    public void discountShippingCosts(Map<String, BigDecimal> shippingCosts) {

    }

    @Override
    public void place() throws OrderException {

    }

    @Override
    public void pay(PaymentDetails details) throws OrderException {

    }

    @Override
    public void fulfil() throws OrderException {

    }

    @Override
    public void cancel(String reason) throws OrderException {

    }

    @Override
    public void sign() throws OrderException {

    }

    @Override
    public void receipt() throws OrderException {

    }

}
