package com.mallfoundry.payment;

import com.mallfoundry.data.jpa.convert.StringStringMapConverter;
import com.mallfoundry.payment.repository.jpa.convert.PaymentSourceConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class InternalPayment implements Payment {

    @Id
    @Column(name = "id_")
    private String id;

    @Column(name = "source_")
    @Convert(converter = PaymentSourceConverter.class)
    private Instrument instrument;

    @Column(name = "reference_")
    private String reference;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_")
    private PaymentStatus status;

    @Column(name = "amount_")
    private BigDecimal amount;

    @Lob
    @Column(name = "metadata_")
    @Convert(converter = StringStringMapConverter.class)
    private Map<String, String> metadata;

    @Column(name = "captured_time_")
    private Date capturedTime;

    @Column(name = "created_time_")
    private Date createdTime;

    public InternalPayment(String id) {
        this.id = id;
    }

    public static InternalPayment of(Payment payment) {
        if (payment instanceof InternalPayment) {
            return (InternalPayment) payment;
        }

        var target = new InternalPayment();
        BeanUtils.copyProperties(payment, target);
        return target;
    }

    @Override
    public void pending() {
        this.setStatus(PaymentStatus.PENDING);
        this.setCreatedTime(new Date());
    }

    @Override
    public void capture() {
        this.setStatus(PaymentStatus.CAPTURED);
        this.setCapturedTime(new Date());
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }
}
