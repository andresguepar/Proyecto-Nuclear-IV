package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.MonthlyReservationDto;
import pn.proyectonuclear4.service.MonthlyReservationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/MonthlyReservations")
public class MonthlyReservationController {

    @Autowired
    private MonthlyReservationService monthlyReservationService;

    @GetMapping("/get")
    public ResponseEntity<List<MonthlyReservationDto>> getAll() {
        return ResponseEntity.ok(monthlyReservationService.getAllMonthlyReservations());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MonthlyReservationDto> getById(@PathVariable int id) {
        Optional<MonthlyReservationDto> reservation = monthlyReservationService.getMonthlyReservationById(id);
        return reservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<MonthlyReservationDto> save(@RequestBody MonthlyReservationDto dto) {
        return ResponseEntity.ok(monthlyReservationService.saveMonthlyReservation(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            monthlyReservationService.deleteMonthlyReservation(id);
            return ResponseEntity.ok("MonthlyReservation status changed successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filterByLotAndDates")
    public ResponseEntity<List<MonthlyReservationDto>> getByParkingLotAndDateRange(
            @RequestParam int parkingLotId,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return ResponseEntity.ok(monthlyReservationService.getMonthlyReservationByParkingLotIdAndDataRange(parkingLotId, start, end));
    }

    @GetMapping("/filterByUser/{userId}")
    public ResponseEntity<List<MonthlyReservationDto>> getByUser(@PathVariable int userId) {
        try {
            return ResponseEntity.ok(monthlyReservationService.getMonthlyReservationsByUser(userId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
