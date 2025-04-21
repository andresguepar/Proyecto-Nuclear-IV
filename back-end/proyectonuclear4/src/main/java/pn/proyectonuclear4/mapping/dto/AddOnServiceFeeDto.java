package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import pn.proyectonuclear4.entity.StandardReservation;

@Builder
public record AddOnServiceFeeDto(
        int idAddOnServiceFee,
        Double total,
        String addOnServices,
        Boolean isActive,
        StandardReservation standardReservation
) {
}