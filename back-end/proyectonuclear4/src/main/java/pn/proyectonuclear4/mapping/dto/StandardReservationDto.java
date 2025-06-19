package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;
import pn.proyectonuclear4.entity.Slot;
import pn.proyectonuclear4.entity.User;
import pn.proyectonuclear4.entity.Payment;
import pn.proyectonuclear4.entity.StatusReservation;

import java.time.LocalDateTime;

@Data
@Builder
public class StandardReservationDto{
        private int idStandardReservation;
        private Slot slot;
        private User user;
        private Payment payment;
        private LocalDateTime scheduledDateTime;
        private LocalDateTime checkIn;
        private LocalDateTime checkOut;
        private LocalDateTime reservationDate;
        private String plate;
        private StatusReservation statusReservation;
}
