package org.mallfoundry.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class InternalInstrument implements PaymentInstrument {

    @Column(name = "instrument_type_")
    private String type;

    public InternalInstrument(String type) {
        this.type = type;
    }
}
