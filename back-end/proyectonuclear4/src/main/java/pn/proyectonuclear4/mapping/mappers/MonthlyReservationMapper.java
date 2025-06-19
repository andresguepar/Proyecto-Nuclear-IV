package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.MonthlyReservation;
import pn.proyectonuclear4.mapping.dto.MonthlyReservationDto;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class MonthlyReservationMapper {
    public static MonthlyReservationDto mapFrom(MonthlyReservation source) {
        return MonthlyReservationDto.builder()
                .idMonthlyReservation(source.getIdMonthlyReservation())
                .slot(source.getSlot())
                .user(source.getUser())
                .payment(source.getPayment())
                .reservationDate(source.getReservationDate())
                .startDate(source.getStartDate())
                .endDate(source.getEndDate())
                .plate(source.getPlate())
                .statusReservation(source.getStatusReservation())
                .build();
    }

    public static MonthlyReservation mapFrom(MonthlyReservationDto source) {
        return MonthlyReservation.builder()
                .idMonthlyReservation(source.getIdMonthlyReservation())
                .slot(source.getSlot())
                .user(source.getUser())
                .payment(source.getPayment())
                .reservationDate(source.getReservationDate())
                .startDate(source.getStartDate())
                .endDate(source.getEndDate())
                .plate(source.getPlate())
                .statusReservation(source.getStatusReservation())
                .build();
    }

    public static List<MonthlyReservationDto> mapFrom(List<MonthlyReservation> source) {
        return source.stream().map(MonthlyReservationMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<MonthlyReservation> mapToEntities(List<MonthlyReservationDto> source) {
        return source.stream().map(MonthlyReservationMapper::mapFrom).collect(Collectors.toList());
    }
}
