package pn.proyectonuclear4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "status_reservation")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idStatusReservation;

    private String name;

    private Boolean isActive;
} 