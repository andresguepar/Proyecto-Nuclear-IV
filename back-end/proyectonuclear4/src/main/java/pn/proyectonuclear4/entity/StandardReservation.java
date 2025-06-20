package pn.proyectonuclear4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "standard_reservations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandardReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idStandardReservation;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private LocalDateTime scheduledDateTime;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private LocalDateTime reservationDate;

    private String plate;

    @ManyToOne
    @JoinColumn(name = "status_reservation_id")
    private StatusReservation statusReservation;
}
