package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.SlotDto;
import java.util.List;
import java.util.Optional;

public interface SlotService {

    List<SlotDto> getAllSlots();

    Optional<SlotDto> getSlotById(int id);

    SlotDto saveSlot(SlotDto slotDto);

    void deleteSlot(int id);

    List<SlotDto> getSlotsByIsActive(Boolean isActive);

    List<SlotDto> getSlotsByParkingLotIdAndVehicleTypeIdAndByIsAvailable(int parkingLotId, int vehicleTypeId, boolean isAvailable);
}
