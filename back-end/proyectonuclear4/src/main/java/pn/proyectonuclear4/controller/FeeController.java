package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.mapping.dto.FeeDto;
import pn.proyectonuclear4.service.FeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Fees")
public class FeeController {

    @Autowired
    private FeeService feeService;

    @GetMapping("/get")
    public ResponseEntity<List<FeeDto>> getAll() {
        return ResponseEntity.ok(feeService.getAllFees());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<FeeDto> getById(@PathVariable int id) {
        Optional<FeeDto> fee = feeService.getFeeById(id);
        return fee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<FeeDto> save(@RequestBody FeeDto dto) {
        return ResponseEntity.ok(feeService.saveFee(dto));
    }
}
