package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.PaymentMethod;
import pn.proyectonuclear4.mapping.dto.PaymentMethodDto;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class PaymentMethodMapper {
    public static PaymentMethodDto mapFrom(PaymentMethod source) {
        return PaymentMethodDto.builder()
                .idPaymentMethod(source.getIdPaymentMethod())
                .name(source.getName())
                .isActive(source.getIsActive())
                .build();
    }

    public static PaymentMethod mapFrom(PaymentMethodDto source) {
        return PaymentMethod.builder()
                .idPaymentMethod(source.idPaymentMethod())
                .name(source.name())
                .isActive(source.isActive())
                .build();
    }

    public static List<PaymentMethodDto> mapFrom(List<PaymentMethod> source) {
        return source.stream().map(PaymentMethodMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<PaymentMethod> mapToEntities(List<PaymentMethodDto> source) {
        return source.stream().map(PaymentMethodMapper::mapFrom).collect(Collectors.toList());
    }
}
