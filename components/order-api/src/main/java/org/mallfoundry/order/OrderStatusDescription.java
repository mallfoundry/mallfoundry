package org.mallfoundry.order;

public interface OrderStatusDescription {

    OrderStatus getStatus();

    void setStatus(OrderStatus status);

    String getLabel();

    void setLabel(String label);

    String getLang();

    void setLang(String lang);
}
