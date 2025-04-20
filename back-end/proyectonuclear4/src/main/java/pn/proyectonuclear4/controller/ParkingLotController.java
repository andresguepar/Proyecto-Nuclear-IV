package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.ParkingLotDto;
import pn.proyectonuclear4.service.ParkingLotService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ParkingLots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping("/get")
    public ResponseEntity<List<ParkingLotDto>> getAll() {
        return ResponseEntity.ok(parkingLotService.getAllParkingLots());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ParkingLotDto> getById(@PathVariable int id) {
        Optional<ParkingLotDto> parkingLot = parkingLotService.getParkingLotById(id);
        return parkingLot.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<ParkingLotDto> save(@RequestBody ParkingLotDto dto) {
        return ResponseEntity.ok(parkingLotService.saveParkingLot(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            parkingLotService.deleteParkingLot(id);
            return ResponseEntity.ok("ParkingLot status changed to inactive successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filterByStatus")
    public ResponseEntity<List<ParkingLotDto>> getByStatus(@RequestParam Boolean isActive) {
        return ResponseEntity.ok(parkingLotService.getParkingLotsByIsActive(isActive));
    }

    @GetMapping("/filterByAdmin")
    public ResponseEntity<List<ParkingLotDto>> getByAdminAndStatus(
            @RequestParam int adminId,
            @RequestParam Boolean isActive) {
        try {
            return ResponseEntity.ok(parkingLotService.getParkingLotsByAdminAndIsActive(adminId, isActive));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
