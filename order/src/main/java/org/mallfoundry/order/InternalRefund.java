package org.mallfoundry.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mallfoundry.order.repository.jpa.convert.RefundItemListConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "order_refunds")
public class InternalRefund implements Refund {

    @Id
    @Column(name = "id_")
    private String id;

    @JsonProperty("order_id")
    @Column(name = "order_id_")
    private String orderId;

    @JsonProperty("total_amount")
    @Column(name = "total_amount_")
    private BigDecimal totalAmount;

    @Convert(converter = RefundItemListConverter.class)
    @Column(name = "items_")
    private List<RefundItem> items;

    @JsonProperty("created_time")
    @Column(name = "created_time_")
    private Date createdTime;

}
