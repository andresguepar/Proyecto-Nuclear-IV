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

/*Este es un controlador REST para manejar las operaciones relacionadas con los días de la semana en la aplicación.
Permite obtener, guardar y consultar días de la semana específicos. Y se espera que muestre cada uno de los dias de
la semana con su respectivo id y nombre. Tal como el siguiente objeto JSON:

[
  {
    "idWeekDay": 1,
    "name": "Lunes"
  },
  {
    "idWeekDay": 2,
    "name": "Martes"
  },
  {
    "idWeekDay": 3,
    "name": "Miércoles"
  },
  {
    "idWeekDay": 4,
    "name": "Jueves"
  },
  {
    "idWeekDay": 5,
    "name": "Viernes"
  },
  {
    "idWeekDay": 6,
    "name": "Sábado"
  },
  {
    "idWeekDay": 7,
    "name": "Domingo"
  }

  ]
  */
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
