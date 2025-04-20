package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.VehicleTypeDto;
import pn.proyectonuclear4.service.VehicleTypeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicle-types")
public class VehicleTypeController {

    @Autowired
    private VehicleTypeService vehicleTypeService;

    @GetMapping("/get")
    public ResponseEntity<List<VehicleTypeDto>> getAllVehicleTypes() {
        return ResponseEntity.ok(vehicleTypeService.getAllVehicleTypes());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<VehicleTypeDto> getVehicleTypeById(@PathVariable int id) {
        Optional<VehicleTypeDto> vehicleType = vehicleTypeService.getVehicleTypeById(id);
        return vehicleType.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<VehicleTypeDto> saveVehicleType(@RequestBody VehicleTypeDto vehicleTypeDto) {
        return ResponseEntity.ok(vehicleTypeService.saveVehicleType(vehicleTypeDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVehicleType(@PathVariable int id) {
        try {
            vehicleTypeService.deleteVehicleType(id);
            return ResponseEntity.ok("Vehicle type marked as inactive.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/is-active/{isActive}")
    public ResponseEntity<List<VehicleTypeDto>> getVehicleTypesByIsActive(@PathVariable Boolean isActive) {
        return ResponseEntity.ok(vehicleTypeService.getVehicleTypesByIsActive(isActive));
    }
}
