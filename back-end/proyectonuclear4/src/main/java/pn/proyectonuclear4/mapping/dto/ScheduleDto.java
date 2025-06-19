package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;
import pn.proyectonuclear4.entity.ParkingLot;
@Data
@Builder
public class ScheduleDto{
        private int idSchedule;
        private ParkingLot parkingLot;
        private Boolean isActive;
}
