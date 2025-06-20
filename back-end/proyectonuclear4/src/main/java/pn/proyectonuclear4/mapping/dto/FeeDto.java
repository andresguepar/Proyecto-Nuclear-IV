package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;
import pn.proyectonuclear4.entity.AddOnService;
import pn.proyectonuclear4.entity.MonthlyFee;
import pn.proyectonuclear4.entity.StandardFee;

@Data
@Builder
public class FeeDto{
        private int idFee;
        private String name;
        private String description;
        private Double total;
        private StandardFee standardFee;
        private MonthlyFee monthlyFee;
        private AddOnService addOnService;
}
