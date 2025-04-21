package pn.proyectonuclear4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "add_on_services_fees")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddOnServiceFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAddOnServiceFee;

    private Double total;

    @Column(columnDefinition = "json")
    private String addOnServices;

    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "standard_reservation_id")
    private StandardReservation standardReservation;
} 