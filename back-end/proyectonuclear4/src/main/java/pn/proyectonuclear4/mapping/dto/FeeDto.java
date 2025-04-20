package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import pn.proyectonuclear4.entity.AddOnServiceFee;
import pn.proyectonuclear4.entity.MonthlyFee;
import pn.proyectonuclear4.entity.StandardFee;

@Builder
public record FeeDto(
        int idFee,
        String name,
        String description,
        Double total,
        StandardFee standardFee,
        MonthlyFee monthlyFee,
        AddOnServiceFee addOnServiceFee
) {
}
