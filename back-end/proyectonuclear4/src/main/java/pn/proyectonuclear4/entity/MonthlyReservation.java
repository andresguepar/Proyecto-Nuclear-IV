package pn.proyectonuclear4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "monthly_reservations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMonthlyReservation;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private LocalDateTime reservationDate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String plate;

    @ManyToOne
    @JoinColumn(name = "status_reservation_id")
    private StatusReservation statusReservation;
} 