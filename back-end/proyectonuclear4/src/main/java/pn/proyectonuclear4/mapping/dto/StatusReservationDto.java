package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusReservationDto{
        private int idStatusReservation;
        private String name;
        private Boolean isActive;

}
