package org.mallfoundry.finance;

import org.mallfoundry.util.ObjectBuilder;

import java.io.Serializable;

public interface PaymentSource extends Serializable, ObjectBuilder.ToBuilder<PaymentSource.Builder> {

    PaymentMethodType getType();

    void setType(PaymentMethodType type);

    String getRedirectUrl();

    void setRedirectUrl(String redirectUrl);

    String getReturnUrl();

    void setReturnUrl(String returnUrl);

    interface Builder extends ObjectBuilder<PaymentSource> {
        Builder type(PaymentMethodType type);
    }
}
