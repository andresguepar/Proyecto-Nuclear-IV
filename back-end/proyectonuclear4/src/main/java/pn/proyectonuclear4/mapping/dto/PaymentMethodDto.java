package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;

@Builder
public record PaymentMethodDto(
        int idPaymentMethod,
        String name,
        Boolean isActive
) {
}
