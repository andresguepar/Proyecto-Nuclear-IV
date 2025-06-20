package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;
import pn.proyectonuclear4.entity.AddOnService;
import pn.proyectonuclear4.entity.StandardReservation;

import java.time.LocalDateTime;

@Data
@Builder
public class ReservationAddOnServiceDto {
    private int idReservationAddOnService;
    private StandardReservation standardReservation;
    private AddOnService addOnService;
    private Double price;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
