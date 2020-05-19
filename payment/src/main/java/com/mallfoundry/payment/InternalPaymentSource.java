package com.mallfoundry.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InternalPaymentSource that = (InternalPaymentSource) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
