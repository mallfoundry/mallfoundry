package com.mallfoundry.payment;

import java.util.Map;

public interface PaymentNotification {

    Map<String, String> getParameters();

    Map<String, String[]> getParameterMap();

    PaymentStatus getStatus();

    void setStatus(PaymentStatus status);

    boolean isPending();

    boolean isCaptured();

    boolean hasResult();

    byte[] getResult();

    void setResult(byte[] bytes);
}
