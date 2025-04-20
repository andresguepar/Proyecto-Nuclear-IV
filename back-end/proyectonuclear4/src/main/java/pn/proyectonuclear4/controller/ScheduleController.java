package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.ScheduleDto;
import pn.proyectonuclear4.service.ScheduleService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/get")
    public ResponseEntity<List<ScheduleDto>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ScheduleDto> getScheduleById(@PathVariable int id) {
        Optional<ScheduleDto> schedule = scheduleService.getScheduleById(id);
        return schedule.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<ScheduleDto> saveSchedule(@RequestBody ScheduleDto scheduleDto) {
        return ResponseEntity.ok(scheduleService.saveSchedule(scheduleDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable int id) {
        try {
            scheduleService.deleteSchedule(id);
            return ResponseEntity.ok("Schedule status updated to inactive.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filterByIsActive/{isActive}")
    public ResponseEntity<List<ScheduleDto>> getSchedulesByIsActive(@PathVariable Boolean isActive) {
        return ResponseEntity.ok(scheduleService.getSchedulesByIsActive(isActive));
    }

    @GetMapping("/filterByParkingLot/{parkingLotId}")
    public ResponseEntity<List<ScheduleDto>> getSchedulesByParkingLot(@PathVariable int parkingLotId) {
        return ResponseEntity.ok(scheduleService.getSchedulesByParkingLot(parkingLotId));
    }
}

