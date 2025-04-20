package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.StatusReservationDto;
import pn.proyectonuclear4.service.StatusReservationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/status-reservations")
public class StatusReservationController {

    @Autowired
    private StatusReservationService statusReservationService;

    @GetMapping("/get")
    public ResponseEntity<List<StatusReservationDto>> getAllStatusReservations() {
        return ResponseEntity.ok(statusReservationService.getAllStatusReservations());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<StatusReservationDto> getStatusReservationById(@PathVariable int id) {
        Optional<StatusReservationDto> statusReservation = statusReservationService.getStatusReservationById(id);
        return statusReservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<StatusReservationDto> saveStatusReservation(@RequestBody StatusReservationDto statusReservationDto) {
        return ResponseEntity.ok(statusReservationService.saveStatusReservation(statusReservationDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStatusReservation(@PathVariable int id) {
        try {
            statusReservationService.deleteStatusReservation(id);
            return ResponseEntity.ok("Status de reserva actualizado a inactivo.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/is-active/{isActive}")
    public ResponseEntity<List<StatusReservationDto>> getStatusReservationsByIsActive(@PathVariable Boolean isActive) {
        return ResponseEntity.ok(statusReservationService.getStatusReservationsByIsActive(isActive));
    }
}
