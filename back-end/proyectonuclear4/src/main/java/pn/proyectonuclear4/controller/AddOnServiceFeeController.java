package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.AddOnServiceFeeDto;
import pn.proyectonuclear4.service.AddOnServiceFeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/AddOnServiceFees")
public class AddOnServiceFeeController {

    @Autowired
    private AddOnServiceFeeService addOnServiceFeeService;

    @GetMapping("/get")
    public ResponseEntity<List<AddOnServiceFeeDto>> getAllFees() {
        return ResponseEntity.ok(addOnServiceFeeService.getAllAddOnServiceFees());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AddOnServiceFeeDto> getFeeById(@PathVariable int id) {
        Optional<AddOnServiceFeeDto> fee = addOnServiceFeeService.getAddOnServiceFeeById(id);
        return fee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<AddOnServiceFeeDto> saveFee(@RequestBody AddOnServiceFeeDto addOnServiceFeeDto) {
        return ResponseEntity.ok(addOnServiceFeeService.saveAddOnServiceFee(addOnServiceFeeDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFee(@PathVariable int id) {
        try {
            addOnServiceFeeService.deleteAddOnServiceFee(id);
            return ResponseEntity.ok("AddOnServiceFee marked as inactive successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<AddOnServiceFeeDto>> getFeesByStatus(@RequestParam Boolean isActive) {
        return ResponseEntity.ok(addOnServiceFeeService.getAddOnServiceFeesByIsActive(isActive));
    }
}
