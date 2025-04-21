package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.AddOnServiceFee;
import pn.proyectonuclear4.mapping.dto.AddOnServiceFeeDto;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class AddOnServiceFeeMapper {
    public static AddOnServiceFeeDto mapFrom(AddOnServiceFee source) {
        return AddOnServiceFeeDto.builder()
                .idAddOnServiceFee(source.getIdAddOnServiceFee())
                .total(source.getTotal())
                .addOnServices(source.getAddOnServices())  // Si `addOnServices` es un string, se usa así
                .isActive(source.getIsActive())
                .standardReservation(source.getStandardReservation())
                .build();
    }

    public static AddOnServiceFee mapFrom(AddOnServiceFeeDto source) {
        return AddOnServiceFee.builder()
                .idAddOnServiceFee(source.idAddOnServiceFee())
                .total(source.total())
                .addOnServices(source.addOnServices())
                .isActive(source.isActive())
                .standardReservation(source.standardReservation())
                .build();
    }

    public static List<AddOnServiceFeeDto> mapFrom(List<AddOnServiceFee> source) {
        return source.stream().map(AddOnServiceFeeMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<AddOnServiceFee> mapToEntities(List<AddOnServiceFeeDto> source) {
        return source.stream().map(AddOnServiceFeeMapper::mapFrom).collect(Collectors.toList());
    }
}
