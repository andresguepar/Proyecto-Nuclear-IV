package pn.proyectonuclear4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "parking_lots")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idParkingLot;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    private String address;

    private String coordX;

    private String coordY;

    private String name;

    private String nit;

    private String phone;

    private Boolean isActive;
} 