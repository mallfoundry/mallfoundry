package org.mallfoundry.payment.methods;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mf_payment_method")
public class InternalPaymentMethod implements PaymentMethod {

    @Id
    @Column(name = "code_")
    private String code;

    @Column(name = "name_")
    private String name;

    @Column(name = "color_")
    private String color;

    @Column(name = "logo_")
    private String logo;

    @Column(name = "description_")
    private String description;

    @Column(name = "enabled_")
    private boolean enabled;

    @Column(name = "position_")
    private int position;

    public InternalPaymentMethod(String code) {
        this.code = code;
    }

    public static InternalPaymentMethod of(PaymentMethod method) {
        if (method instanceof InternalPaymentMethod) {
            return (InternalPaymentMethod) method;
        }
        var target = new InternalPaymentMethod();
        BeanUtils.copyProperties(method, target);
        return target;
    }
}
