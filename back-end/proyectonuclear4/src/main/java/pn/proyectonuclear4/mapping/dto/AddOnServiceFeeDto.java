package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;
import pn.proyectonuclear4.entity.StandardReservation;
@Data
@Builder
public class AddOnServiceFeeDto{
        private int idAddOnServiceFee;
        private Double total;
        private String addOnServices;
        private Boolean isActive;
        private StandardReservation standardReservation;

}