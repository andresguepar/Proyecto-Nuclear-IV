package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.StandardReservationDto;
import pn.proyectonuclear4.service.StandardReservationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/standard-reservations")
public class StandardReservationController {

    @Autowired
    private StandardReservationService standardReservationService;

    @GetMapping("/get")
    public ResponseEntity<List<StandardReservationDto>> getAllStandardReservations() {
        return ResponseEntity.ok(standardReservationService.getAllStandardReservations());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<StandardReservationDto> getStandardReservationById(@PathVariable int id) {
        Optional<StandardReservationDto> standardReservation = standardReservationService.getStandardReservationById(id);
        return standardReservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<StandardReservationDto> saveStandardReservation(@RequestBody StandardReservationDto standardReservationDto) {
        return ResponseEntity.ok(standardReservationService.saveStandardReservation(standardReservationDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStandardReservation(@PathVariable int id) {
        try {
            standardReservationService.deleteStandardReservation(id);
            return ResponseEntity.ok("Standard reservation status updated to canceled.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StandardReservationDto> updateStandardReservation(
            @PathVariable int id,
            @RequestBody StandardReservationDto standardReservationDto) {
        try {
            return ResponseEntity.ok(standardReservationService.updateStandardReservation(id, standardReservationDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StandardReservationDto>> getStandardReservationsByUserId(@PathVariable int userId) {
        return ResponseEntity.ok(standardReservationService.getStandardReservationsByUserId(userId));
    }

    @GetMapping("/parking-lot/{parkingLotId}/date-range")
    public ResponseEntity<List<StandardReservationDto>> getStandardReservationsByParkingLotIdAndDateRange(
            @PathVariable int parkingLotId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(standardReservationService.getStandardReservationsByParkingLotIdAndDateRange(parkingLotId, startDate, endDate));
    }
}
