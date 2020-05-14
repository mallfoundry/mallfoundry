package com.mallfoundry.payment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.CastUtils;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class InternalPaymentNotification implements PaymentNotification {

    private Map<String, String> parameters;

    private Map<String, String[]> parameterMap;

    private PaymentStatus status;

    private byte[] result;

    @Override
    public boolean isPending() {
        return PaymentStatus.PENDING == this.status;
    }

    public InternalPaymentNotification(Object parameterObject) throws PaymentException {
        if (parameterObject instanceof Map) {
            this.parameterMap = CastUtils.cast(parameterObject);
            Map<String, ?> parameterMap = CastUtils.cast(parameterObject);
            this.parameters =
                    parameterMap.entrySet().stream().map(entry -> {
                        if (entry.getValue() instanceof String[]) {
                            String[] values = (String[]) entry.getValue();
                            return Map.entry(entry.getKey(), values[0]);
                        } else {
                            return Map.entry(entry.getKey(), (String) entry.getValue());
                        }
                    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } else {
            throw new PaymentException("");
        }
    }
}
