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
public class ReservationAddOnService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_add_on_service_fee")
    private int idReservationAddOnService;

    @ManyToOne
    @JoinColumn(name = "standard_reservation_id")
    private StandardReservation standardReservation;

    @ManyToOne
    @JoinColumn(name = "add_on_service_id")
    private AddOnService addOnService;

    @Column(name = "price")
    private Double price;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;
} 