package com.mallfoundry.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class InternalPaymentSource implements PaymentSource {

    @Column(name = "source_type_")
    private String type;

    public InternalPaymentSource(String type) {
        this.type = type;
    }
}
