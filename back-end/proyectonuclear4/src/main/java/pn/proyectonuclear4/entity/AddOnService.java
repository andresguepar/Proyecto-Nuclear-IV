package pn.proyectonuclear4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "add_on_services")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddOnService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAddOnService;

    private String name;

    private String description;

    private Double price;

    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "parking_lot_id")
    private ParkingLot parkingLot;
}
