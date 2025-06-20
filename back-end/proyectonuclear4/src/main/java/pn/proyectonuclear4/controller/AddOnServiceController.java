package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.AddOnServiceDto;
import pn.proyectonuclear4.service.AddOnServiceService;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de servicios adicionales (AddOnService).
 * Permite crear, consultar, actualizar y eliminar servicios adicionales,
 * así como filtrarlos por estado o por parqueadero asociado.
 */
@RestController
@RequestMapping("/AddOnServices")
public class AddOnServiceController {

    @Autowired
    private AddOnServiceService addOnServiceService;

    @GetMapping("/get")
    public ResponseEntity<List<AddOnServiceDto>> getAll() {
        return ResponseEntity.ok(addOnServiceService.getAllAddOnServices());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AddOnServiceDto> getById(@PathVariable int id) {
        Optional<AddOnServiceDto> service = addOnServiceService.getAddOnServiceById(id);
        return service.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<AddOnServiceDto> save(@RequestBody AddOnServiceDto dto) {
        return ResponseEntity.ok(addOnServiceService.saveAddOnService(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            addOnServiceService.deleteAddOnService(id);
            return ResponseEntity.ok("AddOnService marked as inactive successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<AddOnServiceDto>> getByStatus(@RequestParam Boolean isActive) {
        return ResponseEntity.ok(addOnServiceService.getAddOnServicesByIsActive(isActive));
    }

    @GetMapping("/by-parking-lot/{id}")
    public ResponseEntity<List<AddOnServiceDto>> getByParkingLot(@PathVariable int id) {
        return ResponseEntity.ok(addOnServiceService.getAddOnServicesByParkingLotId(id));
    }
}
