package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.PaymentMethodDto;
import pn.proyectonuclear4.service.PaymentMethodService;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de métodos de pago (PaymentMethod).
 * Permite crear, consultar, actualizar y eliminar métodos de pago,
 * así como filtrarlos por estado.
 */
@RestController
@RequestMapping("/PaymentMethods")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping("/get")
    public ResponseEntity<List<PaymentMethodDto>> getAll() {
        return ResponseEntity.ok(paymentMethodService.getAllPaymentMethods());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PaymentMethodDto> getById(@PathVariable int id) {
        Optional<PaymentMethodDto> paymentMethod = paymentMethodService.getPaymentMethodById(id);
        return paymentMethod.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<PaymentMethodDto> save(@RequestBody PaymentMethodDto dto) {
        return ResponseEntity.ok(paymentMethodService.savePaymentMethod(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            paymentMethodService.deletePaymentMethod(id);
            return ResponseEntity.ok("PaymentMethod status changed to inactive successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filterByStatus")
    public ResponseEntity<List<PaymentMethodDto>> getByStatus(@RequestParam Boolean isActive) {
        return ResponseEntity.ok(paymentMethodService.getPaymentMethodsByIsActive(isActive));
    }
}
