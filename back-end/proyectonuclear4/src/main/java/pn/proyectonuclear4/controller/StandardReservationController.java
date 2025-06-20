package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.StandardReservationDto;
import pn.proyectonuclear4.service.StandardReservationService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador REST para la gestión de reservas estándar (StandardReservation).
 * Permite crear, consultar, actualizar y eliminar reservas estándar,
 * así como filtrarlas por usuario, estado, parqueadero y slot.
 */
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

    // Nuevos endpoints para el flujo de confirmación
    @PutMapping("/confirm/{id}")
    public ResponseEntity<StandardReservationDto> confirmReservation(@PathVariable int id) {
        try {
            return ResponseEntity.ok(standardReservationService.confirmReservation(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/admin-confirm/{id}")
    public ResponseEntity<StandardReservationDto> adminConfirmReservation(@PathVariable int id) {
        try {
            return ResponseEntity.ok(standardReservationService.adminConfirmReservation(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/start/{id}")
    public ResponseEntity<StandardReservationDto> startReservation(@PathVariable int id) {
        try {
            return ResponseEntity.ok(standardReservationService.startReservation(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<StandardReservationDto> completeReservation(@PathVariable int id) {
        try {
            return ResponseEntity.ok(standardReservationService.completeReservation(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/payment/{id}")
    public ResponseEntity<StandardReservationDto> processPayment(
            @PathVariable int id,
            @RequestParam int paymentMethodId) {
        try {
            return ResponseEntity.ok(standardReservationService.processPayment(id, paymentMethodId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/parking-lot-admin/{adminId}")
    public ResponseEntity<List<StandardReservationDto>> getStandardReservationsByParkingLotAdmin(@PathVariable int adminId) {
        System.out.println("DEBUG: Endpoint llamado con adminId: " + adminId);
        List<StandardReservationDto> reservations = standardReservationService.getStandardReservationsByParkingLotAdmin(adminId);
        System.out.println("DEBUG: Endpoint retornando " + reservations.size() + " reservas");
        return ResponseEntity.ok(reservations);
    }

    @PutMapping("/user-request-start/{id}")
    public ResponseEntity<StandardReservationDto> userRequestStart(@PathVariable int id) {
        try {
            return ResponseEntity.ok(standardReservationService.userRequestStart(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/debug/admin-info/{adminId}")
    public ResponseEntity<Map<String, Object>> debugAdminInfo(@PathVariable int adminId) {
        Map<String, Object> debugInfo = new HashMap<>();

        try {
            // Contar parking lots del admin
            int parkingLotsCount = standardReservationService.countParkingLotsByAdmin(adminId);
            debugInfo.put("adminId", adminId);
            debugInfo.put("parkingLotsCount", parkingLotsCount);

            // Obtener reservas con consulta original
            List<StandardReservationDto> reservationsOriginal = standardReservationService.getStandardReservationsByParkingLotAdmin(adminId);
            debugInfo.put("reservationsCount", reservationsOriginal.size());

            return ResponseEntity.ok(debugInfo);
        } catch (Exception e) {
            debugInfo.put("error", e.getMessage());
            return ResponseEntity.ok(debugInfo);
        }
    }
}
