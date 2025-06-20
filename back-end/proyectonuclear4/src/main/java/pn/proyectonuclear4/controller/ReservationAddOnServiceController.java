package pn.proyectonuclear4.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.ReservationAddOnServiceDto;
import pn.proyectonuclear4.service.ReservationAddOnServiceService;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de servicios adicionales asociados a reservas (ReservationAddOnService).
 * Permite crear, consultar, actualizar y eliminar servicios adicionales vinculados a una reserva estándar,
 * así como obtener todos los servicios activos o por reserva específica.
 */
@RestController
@RequestMapping("/AddOnServiceFees")
@CrossOrigin(origins = "*")
public class ReservationAddOnServiceController {
    private static final Logger logger = LoggerFactory.getLogger(ReservationAddOnServiceController.class);

    @Autowired
    private ReservationAddOnServiceService reservationAddOnServiceService;

    @GetMapping("/get")
    public ResponseEntity<List<ReservationAddOnServiceDto>> getAll() {
        try {
            return ResponseEntity.ok(reservationAddOnServiceService.getAllReservationAddOnServices());
        } catch (Exception e) {
            logger.error("Error getting all reservation add-on services", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ReservationAddOnServiceDto> getById(@PathVariable int id) {
        try {
            Optional<ReservationAddOnServiceDto> service = reservationAddOnServiceService.getReservationAddOnServiceById(id);
            return service.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error getting reservation add-on service by id: " + id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ReservationAddOnServiceDto dto) {
        try {
            logger.info("Saving reservation add-on service: {}", dto);
            ReservationAddOnServiceDto savedService = reservationAddOnServiceService.saveReservationAddOnService(dto);
            return ResponseEntity.ok(savedService);
        } catch (Exception e) {
            logger.error("Error saving reservation add-on service", e);
            return ResponseEntity.internalServerError()
                    .body("Error saving reservation add-on service: " + e.getMessage());
        }
    }

    @PostMapping("/save-multiple/{reservationId}")
    public ResponseEntity<?> saveMultipleForReservation(@PathVariable int reservationId, @RequestBody List<ReservationAddOnServiceDto> services) {
        try {
            logger.info("Saving multiple add-on services for reservation: {}", reservationId);
            List<ReservationAddOnServiceDto> savedServices = reservationAddOnServiceService.saveMultipleForReservation(reservationId, services);
            return ResponseEntity.ok(savedServices);
        } catch (Exception e) {
            logger.error("Error saving multiple add-on services for reservation: " + reservationId, e);
            return ResponseEntity.internalServerError()
                    .body("Error saving multiple add-on services: " + e.getMessage());
        }
    }

    @GetMapping("/by-reservation/{reservationId}")
    public ResponseEntity<List<ReservationAddOnServiceDto>> getByReservationId(@PathVariable int reservationId) {
        try {
            return ResponseEntity.ok(reservationAddOnServiceService.getByReservationId(reservationId));
        } catch (Exception e) {
            logger.error("Error getting add-on services for reservation: " + reservationId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/active-by-reservation/{reservationId}")
    public ResponseEntity<List<ReservationAddOnServiceDto>> getActiveByReservationId(@PathVariable int reservationId) {
        try {
            return ResponseEntity.ok(reservationAddOnServiceService.getActiveByReservationId(reservationId));
        } catch (Exception e) {
            logger.error("Error getting active add-on services for reservation: " + reservationId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            reservationAddOnServiceService.deleteReservationAddOnService(id);
            return ResponseEntity.ok("ReservationAddOnService marked as inactive successfully.");
        } catch (ResourceNotFoundException e) {
            logger.warn("ReservationAddOnService not found with id: " + id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error deleting reservation add-on service with id: " + id, e);
            return ResponseEntity.internalServerError()
                    .body("Error deleting reservation add-on service: " + e.getMessage());
        }
    }
} 