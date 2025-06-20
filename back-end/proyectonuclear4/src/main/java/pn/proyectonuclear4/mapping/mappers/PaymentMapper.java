package pn.proyectonuclear4.mapping.mappers;

import lombok.Builder;
import pn.proyectonuclear4.entity.Payment;
import pn.proyectonuclear4.mapping.dto.PaymentDto;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class PaymentMapper {
    public static PaymentDto mapFrom(Payment source) {
        return PaymentDto.builder()
                .idPayment(source.getIdPayment())
                .paymentDate(source.getPaymentDate())
                .status(source.getStatus())
                .paymentMethod(source.getPaymentMethod())
                .fee(source.getFee())
                .build();
    }

    public static Payment mapFrom(PaymentDto source) {
        return Payment.builder()
                .idPayment(source.getIdPayment())
                .paymentDate(source.getPaymentDate())
                .status(source.getStatus())
                .paymentMethod(source.getPaymentMethod())
                .fee(source.getFee())
                .build();
    }

    public static List<PaymentDto> mapFrom(List<Payment> source) {
        return source.stream().map(PaymentMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<Payment> mapToEntities(List<PaymentDto> source) {
        return source.stream().map(PaymentMapper::mapFrom).collect(Collectors.toList());
    }
}
