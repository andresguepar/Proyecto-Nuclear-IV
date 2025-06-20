package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.MonthlyFeeDto;
import pn.proyectonuclear4.service.MonthlyFeeService;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de tarifas mensuales (MonthlyFee).
 * Permite crear, consultar, actualizar y eliminar tarifas mensuales,
 * así como filtrarlas por estado.
 */
@RestController
@RequestMapping("/MonthlyFees")
public class MonthlyFeeController {

    @Autowired
    private MonthlyFeeService monthlyFeeService;

    @GetMapping("/get")
    public ResponseEntity<List<MonthlyFeeDto>> getAll() {
        return ResponseEntity.ok(monthlyFeeService.getAllMonthlyFees());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MonthlyFeeDto> getById(@PathVariable int id) {
        Optional<MonthlyFeeDto> fee = monthlyFeeService.getMonthlyFeeById(id);
        return fee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<MonthlyFeeDto> save(@RequestBody MonthlyFeeDto dto) {
        return ResponseEntity.ok(monthlyFeeService.saveMonthlyFee(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            monthlyFeeService.deleteMonthlyFee(id);
            return ResponseEntity.ok("MonthlyFee marked as inactive successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<MonthlyFeeDto>> getByStatus(@RequestParam Boolean isActive) {
        return ResponseEntity.ok(monthlyFeeService.getMonthlyFeesByIsActive(isActive));
    }
}
