package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;

@Builder
public record AddOnServiceFeeDto(
        int idAddOnServiceFee,
        Double total,
        String addOnServices,
        Boolean isActive
) {
}