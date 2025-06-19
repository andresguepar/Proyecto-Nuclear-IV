package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;
import pn.proyectonuclear4.entity.Payment;
import pn.proyectonuclear4.entity.Slot;
import pn.proyectonuclear4.entity.StatusReservation;
import pn.proyectonuclear4.entity.User;

import java.time.LocalDateTime;

@Data
@Builder
public class MonthlyReservationDto{
        private int idMonthlyReservation;
        private Slot slot;
        private User user;
        private Payment payment;
        private LocalDateTime reservationDate;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String plate;
        private StatusReservation statusReservation;
}
