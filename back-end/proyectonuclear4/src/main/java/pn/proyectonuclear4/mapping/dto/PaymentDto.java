package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import pn.proyectonuclear4.entity.PaymentMethod;
import pn.proyectonuclear4.entity.Fee;

import java.time.LocalDateTime;

@Builder
public record PaymentDto(
        int idPayment,
        LocalDateTime paymentDate,
        String status,
        PaymentMethod paymentMethod,
        Fee fee
) {
}
