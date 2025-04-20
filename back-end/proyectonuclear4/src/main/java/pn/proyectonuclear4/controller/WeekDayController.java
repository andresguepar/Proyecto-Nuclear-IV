package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.mapping.dto.WeekDayDto;
import pn.proyectonuclear4.service.WeekDayService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/week-days")
public class WeekDayController {

    @Autowired
    private WeekDayService weekDayService;

    @GetMapping("/get")
    public ResponseEntity<List<WeekDayDto>> getAllWeekDays() {
        return ResponseEntity.ok(weekDayService.getAllWeekDays());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<WeekDayDto> getWeekDayById(@PathVariable int id) {
        Optional<WeekDayDto> weekDay = weekDayService.getWeekDayById(id);
        return weekDay.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<WeekDayDto> saveWeekDay(@RequestBody WeekDayDto weekDayDto) {
        return ResponseEntity.ok(weekDayService.saveWeekDay(weekDayDto));
    }
}
