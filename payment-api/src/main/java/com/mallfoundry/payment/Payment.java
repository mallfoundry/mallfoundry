package com.mallfoundry.payment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public interface Payment {

    String getId();

    PaymentSource getSource();

    void setSource(PaymentSource source);

    String getReference();

    void setReference(String reference);

    PaymentStatus getStatus();

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);

    Map<String, String> getMetadata();

    void setMetadata(Map<String, String> metadata);

    Date getCapturedTime();

    Date getCreatedTime();

    void pending();

    Builder toBuilder();

    class Builder {

        private final Payment payment;

        public Builder(Payment payment) {
            this.payment = payment;
        }

        public Builder amount(BigDecimal amount) {
            this.payment.setAmount(amount);
            return this;
        }

        public Builder reference(String reference) {
            this.payment.setReference(reference);
            return this;
        }

        public Builder metadata(Map<String, String> metadata) {
            this.payment.setMetadata(metadata);
            return this;
        }

        public Builder source(PaymentSource source) {
            this.payment.setSource(source);
            return this;
        }

        public Payment build() {
            return this.payment;
        }
    }
}
