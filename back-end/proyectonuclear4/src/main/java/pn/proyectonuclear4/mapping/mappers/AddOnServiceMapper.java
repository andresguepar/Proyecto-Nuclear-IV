package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.AddOnService;
import pn.proyectonuclear4.mapping.dto.AddOnServiceDto;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddOnServiceMapper {
    public AddOnServiceDto mapFrom(AddOnService source) {
        if (source == null) {
            return null;
        }
        return new AddOnServiceDto(
            source.getIdAddOnService(),
            source.getName(),
            source.getDescription(),
            source.getPrice(),
            source.getIsActive(),
            source.getParkingLot()
        );
    }

    public AddOnService mapFrom(AddOnServiceDto source) {
        if (source == null) {
            return null;
        }
        return AddOnService.builder()
                .idAddOnService(source.idAddOnService())
                .name(source.name())
                .description(source.description())
                .price(source.price())
                .isActive(source.isActive())
                .parkingLot(source.parkingLot())
                .build();
    }

    public List<AddOnServiceDto> mapFrom(List<AddOnService> source) {
        if (source == null) {
            return Collections.emptyList();
        }
        return source.stream()
                .map(this::mapFrom)
                .collect(Collectors.toList());
    }

    public List<AddOnService> mapToEntities(List<AddOnServiceDto> source) {
        if (source == null) {
            return Collections.emptyList();
        }
        return source.stream()
                .map(this::mapFrom)
                .collect(Collectors.toList());
    }
}
