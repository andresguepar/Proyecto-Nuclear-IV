package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.PaymentDto;
import java.util.List;
import java.util.Optional;

public interface PaymentService {

    List<PaymentDto> getAllPayments();

    Optional<PaymentDto> getPaymentById(int id);

    PaymentDto savePayment(PaymentDto paymentDto);

    void deletePayment(int id);

    List<PaymentDto> getPaymentsByPaymentMethod(int paymentMethodId);

    List<PaymentDto> getPaymentsByParkingLotId(int parkingLotId);

    List<PaymentDto> getPaymentsByUserId(int userId);

}
