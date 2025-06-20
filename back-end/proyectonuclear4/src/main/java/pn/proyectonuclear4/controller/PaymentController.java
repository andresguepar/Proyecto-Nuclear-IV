package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.PaymentDto;
import pn.proyectonuclear4.service.PaymentService;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de pagos (Payment).
 * Permite crear, consultar y listar pagos realizados por los usuarios,
 * así como filtrarlos por estado, método de pago y usuario.
 */
@RestController
@RequestMapping("/Payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/get")
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable int id) {
        Optional<PaymentDto> payment = paymentService.getPaymentById(id);
        return payment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<PaymentDto> savePayment(@RequestBody PaymentDto paymentDto) {
        return ResponseEntity.ok(paymentService.savePayment(paymentDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable int id) {
        try {
            paymentService.deletePayment(id);
            return ResponseEntity.ok("Payment status updated to deleted.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filterByPaymentMethod/{paymentMethodId}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByPaymentMethod(@PathVariable int paymentMethodId) {
        return ResponseEntity.ok(paymentService.getPaymentsByPaymentMethod(paymentMethodId));
    }

    @GetMapping("/filterByParkingLot/{parkingLotId}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByParkingLot(@PathVariable int parkingLotId) {
        return ResponseEntity.ok(paymentService.getPaymentsByParkingLotId(parkingLotId));
    }

    @GetMapping("/filterByUser/{userId}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByUser(@PathVariable int userId) {
        return ResponseEntity.ok(paymentService.getPaymentsByUserId(userId));
    }
}
