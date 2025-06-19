package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.StandardReservation;
import pn.proyectonuclear4.mapping.dto.StandardReservationDto;

import java.util.List;
import java.util.stream.Collectors;

public class StandardReservationMapper {
    public static StandardReservationDto mapFrom(StandardReservation source) {
        return StandardReservationDto.builder()
                .idStandardReservation(source.getIdStandardReservation())
                .slot(source.getSlot())
                .user(source.getUser())
                .payment(source.getPayment())
                .scheduledDateTime(source.getScheduledDateTime())
                .checkIn(source.getCheckIn())
                .checkOut(source.getCheckOut())
                .reservationDate(source.getReservationDate())
                .plate(source.getPlate())
                .statusReservation(source.getStatusReservation())
                .build();
    }

    public static StandardReservation mapFrom(StandardReservationDto source) {
        return StandardReservation.builder()
                .idStandardReservation(source.getIdStandardReservation())
                .slot(source.getSlot())
                .user(source.getUser())
                .payment(source.getPayment())
                .scheduledDateTime(source.getScheduledDateTime())
                .checkIn(source.getCheckIn())
                .checkOut(source.getCheckOut())
                .reservationDate(source.getReservationDate())
                .plate(source.getPlate())
                .statusReservation(source.getStatusReservation())
                .build();
    }

    public static List<StandardReservationDto> mapFrom(List<StandardReservation> source) {
        return source.stream().map(StandardReservationMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<StandardReservation> mapToEntities(List<StandardReservationDto> source) {
        return source.stream().map(StandardReservationMapper::mapFrom).collect(Collectors.toList());
    }
}
