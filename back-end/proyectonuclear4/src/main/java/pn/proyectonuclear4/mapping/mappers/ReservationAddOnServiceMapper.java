package pn.proyectonuclear4.mapping.mappers;

import lombok.Builder;
import pn.proyectonuclear4.entity.ReservationAddOnService;
import pn.proyectonuclear4.mapping.dto.ReservationAddOnServiceDto;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class ReservationAddOnServiceMapper {
    public static ReservationAddOnServiceDto mapFrom(ReservationAddOnService source) {
        if (source == null) return null;

        return ReservationAddOnServiceDto.builder()
                .idReservationAddOnService(source.getIdReservationAddOnService())
                .standardReservation(source.getStandardReservation())
                .addOnService(source.getAddOnService())
                .price(source.getPrice())
                .isActive(source.getIsActive())
                .createdAt(source.getCreatedAt())
                .updatedAt(source.getUpdatedAt())
                .build();
    }

    public static ReservationAddOnService mapFrom(ReservationAddOnServiceDto source) {
        if (source == null) return null;

        return ReservationAddOnService.builder()
                .idReservationAddOnService(source.getIdReservationAddOnService())
                .standardReservation(source.getStandardReservation())
                .addOnService(source.getAddOnService())
                .price(source.getPrice())
                .isActive(source.getIsActive())
                .createdAt(source.getCreatedAt())
                .updatedAt(source.getUpdatedAt())
                .build();
    }

    public static List<ReservationAddOnServiceDto> mapFrom(List<ReservationAddOnService> source) {
        if (source == null) return null;
        return source.stream()
                .filter(item -> item != null)
                .map(ReservationAddOnServiceMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public static List<ReservationAddOnService> mapToEntities(List<ReservationAddOnServiceDto> source) {
        if (source == null) return null;
        return source.stream()
                .filter(item -> item != null)
                .map(ReservationAddOnServiceMapper::mapFrom)
                .collect(Collectors.toList());
    }
}
