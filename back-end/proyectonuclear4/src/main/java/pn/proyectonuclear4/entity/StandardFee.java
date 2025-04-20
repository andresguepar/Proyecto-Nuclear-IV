package pn.proyectonuclear4.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "standard_fees")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandardFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idStandardFee;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id")
    private VehicleType vehicleType;

    @ManyToOne
    @JoinColumn(name = "parking_lot_id")
    private ParkingLot parkingLot;

    private Double priceForHours;

    private Boolean isActivePriceFortTwelveHours;

    private Double priceForTwelveHours;

    private Boolean isActive;
} 