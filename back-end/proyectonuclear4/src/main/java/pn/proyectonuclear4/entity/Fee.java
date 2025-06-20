package pn.proyectonuclear4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "add_on_service_id")
    private AddOnService addOnService;
}
