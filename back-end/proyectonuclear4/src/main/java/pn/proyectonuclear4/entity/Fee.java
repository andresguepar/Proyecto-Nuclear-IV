package pn.proyectonuclear4.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Entity
@Table(name = "fees")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFee;

    private String name;

    private String description;

    private Double total;

    @ManyToOne
    @JoinColumn(name = "standard_fee_id")
    private StandardFee standardFee;

    @ManyToOne
    @JoinColumn(name = "monthly_fee_id")
    private MonthlyFee monthlyFee;

    @ManyToOne
    @JoinColumn(name = "add_on_services_fee_id")
    private AddOnServiceFee addOnServiceFee;


} 