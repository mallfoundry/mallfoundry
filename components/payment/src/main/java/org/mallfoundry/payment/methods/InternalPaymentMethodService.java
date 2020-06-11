package org.mallfoundry.payment.methods;

import org.springframework.data.util.CastUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InternalPaymentMethodService implements PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    public InternalPaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public PaymentMethod createPaymentMethod(String code) {
        return new InternalPaymentMethod(code);
    }

    @Override
    public PaymentMethodQuery createPaymentMethodQuery() {
        return new InternalPaymentMethodQuery();
    }

    @Override
    public PaymentMethod addPaymentMethod(PaymentMethod method) {
        return this.paymentMethodRepository.save(InternalPaymentMethod.of(method));
    }

    @Override
    public PaymentMethod updatePaymentMethod(PaymentMethod method) {
        return this.paymentMethodRepository.save(InternalPaymentMethod.of(method));
    }

    @Override
    public List<PaymentMethod> getPaymentMethods(PaymentMethodQuery query) {
        return CastUtils.cast(this.paymentMethodRepository.findAll(query));
    }

    @Override
    public Optional<PaymentMethod> getPaymentMethod(String code) {
        return CastUtils.cast(this.paymentMethodRepository.findById(code));
    }

    @Override
    public void deletePaymentMethod(String code) {
        var method = this.paymentMethodRepository.findById(code).orElseThrow();
        this.paymentMethodRepository.delete(method);
    }
}
