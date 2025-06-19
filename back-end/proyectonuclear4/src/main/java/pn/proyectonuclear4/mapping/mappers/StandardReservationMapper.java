package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.StandardReservation;
import pn.proyectonuclear4.mapping.dto.StandardReservationDto;

import java.util.List;
import java.util.stream.Collectors;

public class StandardReservationMapper {
    public static StandardReservationDto mapFrom(StandardReservation source) {
        return new StandardReservationDto(
                source.getIdStandardReservation(),
                source.getSlot(),
                source.getUser(),
                source.getPayment(),
                source.getScheduledDateTime(),
                source.getCheckIn(),
                source.getCheckOut(),
                source.getReservationDate(),
                source.getPlate(),
                source.getStatusReservation()
        );
    }

    public static StandardReservation mapFrom(StandardReservationDto source) {
        return StandardReservation.builder()
                .idStandardReservation(source.idStandardReservation())
                .slot(source.slot())
                .user(source.user())
                .payment(source.payment())
                .scheduledDateTime(source.scheduledDateTime())
                .checkIn(source.checkIn())
                .checkOut(source.checkOut())
                .reservationDate(source.reservationDate())
                .plate(source.plate())
                .statusReservation(source.statusReservation())
                .build();
    }

    public static List<StandardReservationDto> mapFrom(List<StandardReservation> source) {
        return source.stream().map(StandardReservationMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<StandardReservation> mapToEntities(List<StandardReservationDto> source) {
        return source.stream().map(StandardReservationMapper::mapFrom).collect(Collectors.toList());
    }
}
