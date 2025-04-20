package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.PaymentMethodDto;
import java.util.List;
import java.util.Optional;

public interface PaymentMethodService {

    List<PaymentMethodDto> getAllPaymentMethods();

    Optional<PaymentMethodDto> getPaymentMethodById(int id);

    PaymentMethodDto savePaymentMethod(PaymentMethodDto paymentMethodDto);

    void deletePaymentMethod(int id);

    List<PaymentMethodDto> getPaymentMethodsByIsActive(Boolean isActive);

}
