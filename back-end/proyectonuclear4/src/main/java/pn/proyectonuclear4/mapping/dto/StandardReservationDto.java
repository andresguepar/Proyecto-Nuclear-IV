package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import pn.proyectonuclear4.entity.Slot;
import pn.proyectonuclear4.entity.User;
import pn.proyectonuclear4.entity.Payment;
import pn.proyectonuclear4.entity.StatusReservation;

import java.time.LocalDateTime;

@Builder
public record StandardReservationDto(
        int idStandardReservation,
        Slot slot,
        User user,
        Payment payment,
        LocalDateTime scheduledDateTime,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        LocalDateTime reservationDate,
        String plate,
        StatusReservation statusReservation
) {
}
