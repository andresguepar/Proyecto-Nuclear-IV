package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import pn.proyectonuclear4.entity.Payment;
import pn.proyectonuclear4.entity.Slot;
import pn.proyectonuclear4.entity.StatusReservation;
import pn.proyectonuclear4.entity.User;

import java.time.LocalDateTime;

@Builder
public record MonthlyReservationDto(
        int idMonthlyReservation,
        Slot slot,
        User user,
        Payment payment,
        LocalDateTime reservationDate,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String plate,
        StatusReservation statusReservation
) {
}
