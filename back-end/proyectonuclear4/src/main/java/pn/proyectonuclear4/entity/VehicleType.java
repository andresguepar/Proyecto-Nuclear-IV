package pn.proyectonuclear4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "vehicle_types")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVehicleType;

    private String name;

    private Boolean isActive;
} 