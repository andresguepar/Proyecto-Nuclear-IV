package pn.proyectonuclear4.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Entity
@Table(name = "monthly_fees")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMonthlyFee;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id")
    private VehicleType vehicleType;

    @ManyToOne
    @JoinColumn(name = "parking_lot_id")
    private ParkingLot parkingLot;

    private Double price;

    private Boolean isActive;
} 