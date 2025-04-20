package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.SlotDto;
import pn.proyectonuclear4.service.SlotService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/slots")
public class SlotController {

    @Autowired
    private SlotService slotService;

    @GetMapping("/get")
    public ResponseEntity<List<SlotDto>> getAllSlots() {
        return ResponseEntity.ok(slotService.getAllSlots());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SlotDto> getSlotById(@PathVariable int id) {
        Optional<SlotDto> slot = slotService.getSlotById(id);
        return slot.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<SlotDto> saveSlot(@RequestBody SlotDto slotDto) {
        return ResponseEntity.ok(slotService.saveSlot(slotDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSlot(@PathVariable int id) {
        try {
            slotService.deleteSlot(id);
            return ResponseEntity.ok("Slot status updated to inactive.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filterByIsActive/{isActive}")
    public ResponseEntity<List<SlotDto>> getSlotsByIsActive(@PathVariable Boolean isActive) {
        return ResponseEntity.ok(slotService.getSlotsByIsActive(isActive));
    }

    @GetMapping("/filterByParkingLotAndVehicleType/{parkingLotId}/{vehicleTypeId}/{isAvailable}")
    public ResponseEntity<List<SlotDto>> getSlotsByParkingLotIdAndVehicleTypeIdAndByIsAvailable(
            @PathVariable int parkingLotId, @PathVariable int vehicleTypeId, @PathVariable boolean isAvailable) {
        return ResponseEntity.ok(slotService.getSlotsByParkingLotIdAndVehicleTypeIdAndByIsAvailable(parkingLotId, vehicleTypeId, isAvailable));
    }
}
