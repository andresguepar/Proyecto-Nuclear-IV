package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.DailyScheduleDto;
import pn.proyectonuclear4.service.DailyScheduleService;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de los horarios diarios (DailySchedule).
 * Permite crear, consultar, actualizar y eliminar horarios diarios,
 * así como filtrarlos por estado, día de la semana, parqueadero y combinación de ambos.
 */
@RestController
@RequestMapping("/DailySchedules")
public class DailyScheduleController {

    @Autowired
    private DailyScheduleService dailyScheduleService;

    @GetMapping("/get")
    public ResponseEntity<List<DailyScheduleDto>> getAll() {
        return ResponseEntity.ok(dailyScheduleService.getAllDailySchedules());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DailyScheduleDto> getById(@PathVariable int id) {
        Optional<DailyScheduleDto> schedule = dailyScheduleService.getDailyScheduleById(id);
        return schedule.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<DailyScheduleDto> save(@RequestBody DailyScheduleDto dto) {
        return ResponseEntity.ok(dailyScheduleService.saveDailySchedule(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            dailyScheduleService.deleteDailySchedule(id);
            return ResponseEntity.ok("DailySchedule marked as inactive successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<DailyScheduleDto>> getByStatus(@RequestParam Boolean isActive) {
        return ResponseEntity.ok(dailyScheduleService.getDailySchedulesByIsActive(isActive));
    }


    @GetMapping("/filter/by-schedule-weekday")
    public ResponseEntity<List<DailyScheduleDto>> getByScheduleAndWeekDay(
            @RequestParam int scheduleId,
            @RequestParam int weekDayId,
            @RequestParam Boolean isActive
    ) {
        return ResponseEntity.ok(
                dailyScheduleService.getDailySchedulesByWeekDayAndScheduleAndIsActive(scheduleId, weekDayId, isActive)
        );
    }

    @GetMapping("/parking-lot/{parkingLotId}")
    public ResponseEntity<List<DailyScheduleDto>> getByParkingLot(@PathVariable int parkingLotId) {
        return ResponseEntity.ok(dailyScheduleService.getDailySchedulesByParkingLot(parkingLotId));
    }
}
