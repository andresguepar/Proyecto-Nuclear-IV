package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.Payment;
import pn.proyectonuclear4.mapping.dto.PaymentDto;
import lombok.Builder;

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
                .idPayment(source.idPayment())
                .paymentDate(source.paymentDate())
                .status(source.status())
                .paymentMethod(source.paymentMethod())
                .fee(source.fee())
                .build();
    }

    public static List<PaymentDto> mapFrom(List<Payment> source) {
        return source.stream().map(PaymentMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<Payment> mapToEntities(List<PaymentDto> source) {
        return source.stream().map(PaymentMapper::mapFrom).collect(Collectors.toList());
    }
}
