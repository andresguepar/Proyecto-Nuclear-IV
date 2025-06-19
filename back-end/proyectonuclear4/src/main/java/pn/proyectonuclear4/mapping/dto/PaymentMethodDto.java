package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentMethodDto{
        private int idPaymentMethod;
        private String name;
        private Boolean isActive;

}
