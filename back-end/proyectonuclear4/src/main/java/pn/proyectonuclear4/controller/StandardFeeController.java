package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.StandardFeeDto;
import pn.proyectonuclear4.service.StandardFeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/standard-fees")
public class StandardFeeController {

    @Autowired
    private StandardFeeService standardFeeService;

    @GetMapping("/get")
    public ResponseEntity<List<StandardFeeDto>> getAllStandardFees() {
        return ResponseEntity.ok(standardFeeService.getAllStandardFees());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<StandardFeeDto> getStandardFeeById(@PathVariable int id) {
        Optional<StandardFeeDto> standardFee = standardFeeService.getStandardFeeById(id);
        return standardFee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<StandardFeeDto> saveStandardFee(@RequestBody StandardFeeDto standardFeeDto) {
        return ResponseEntity.ok(standardFeeService.saveStandardFee(standardFeeDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStandardFee(@PathVariable int id) {
        try {
            standardFeeService.deleteStandardFee(id);
            return ResponseEntity.ok("Standard fee status updated to inactive.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filterByIsActive/{isActive}")
    public ResponseEntity<List<StandardFeeDto>> getStandardFeesByIsActive(@PathVariable Boolean isActive) {
        return ResponseEntity.ok(standardFeeService.getStandardFeesByIsActive(isActive));
    }
}
