package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.AddOnService;
import pn.proyectonuclear4.mapping.dto.AddOnServiceDto;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class AddOnServiceMapper {
    public static AddOnServiceDto mapFrom(AddOnService source) {
        return AddOnServiceDto.builder()
                .idAddOnService(source.getIdAddOnService())
                .name(source.getName())
                .description(source.getDescription())
                .price(source.getPrice())
                .isActive(source.getIsActive())
                .parkingLot(source.getParkingLot())
                .build();
    }

    public static AddOnService mapFrom(AddOnServiceDto source) {
        return AddOnService.builder()
                .idAddOnService(source.idAddOnService())
                .name(source.name())
                .description(source.description())
                .price(source.price())
                .isActive(source.isActive())
                .parkingLot(source.parkingLot())
                .build();
    }

    public static List<AddOnServiceDto> mapFrom(List<AddOnService> source) {
        return source.stream().map(AddOnServiceMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<AddOnService> mapToEntities(List<AddOnServiceDto> source) {
        return source.stream().map(AddOnServiceMapper::mapFrom).collect(Collectors.toList());
    }
}
