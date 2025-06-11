package pn.proyectonuclear4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pn.proyectonuclear4.entity.Payment;
import pn.proyectonuclear4.entity.PaymentMethod;
import pn.proyectonuclear4.entity.User;
import pn.proyectonuclear4.entity.Fee;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.PaymentDto;
import pn.proyectonuclear4.mapping.mappers.PaymentMapper;
import pn.proyectonuclear4.repository.PaymentMethodRepository;
import pn.proyectonuclear4.repository.PaymentRepository;
import pn.proyectonuclear4.service.impl.PaymentServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Payment payment;
    private PaymentDto paymentDto;
    private PaymentMethod paymentMethod;
    private Fee fee;

    @BeforeEach
    void setUp() {
        paymentMethod = PaymentMethod.builder()
                .idPaymentMethod(1)
                .name("Credit Card")
                .build();

        fee = Fee.builder()
                .idFee(1)
                .build();

        payment = Payment.builder()
                .idPayment(1)
                .paymentDate(LocalDateTime.now())
                .status("active")
                .paymentMethod(paymentMethod)
                .fee(fee)
                .build();

        paymentDto = PaymentMapper.mapFrom(payment);
    }

    @Test
    void getAllPayments_ShouldReturnAllPayments() {
        when(paymentRepository.findAll()).thenReturn(Arrays.asList(payment));

        List<PaymentDto> result = paymentService.getAllPayments();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(paymentRepository).findAll();
    }

    @Test
    void getPaymentById_WhenExists_ShouldReturnPayment() {
        when(paymentRepository.findById(1)).thenReturn(Optional.of(payment));

        Optional<PaymentDto> result = paymentService.getPaymentById(1);

        assertTrue(result.isPresent());
        assertEquals(payment.getIdPayment(), result.get().idPayment());
        verify(paymentRepository).findById(1);
    }

    @Test
    void getPaymentById_WhenNotExists_ShouldReturnEmpty() {
        when(paymentRepository.findById(999)).thenReturn(Optional.empty());

        Optional<PaymentDto> result = paymentService.getPaymentById(999);

        assertFalse(result.isPresent());
        verify(paymentRepository).findById(999);
    }

    @Test
    void savePayment_ShouldSaveAndReturnPayment() {
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentDto result = paymentService.savePayment(paymentDto);

        assertNotNull(result);
        assertEquals(payment.getIdPayment(), result.idPayment());
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void deletePayment_WhenExists_ShouldMarkAsDeleted() {
        when(paymentRepository.findById(1)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        paymentService.deletePayment(1);

        verify(paymentRepository).findById(1);
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void deletePayment_WhenNotExists_ShouldThrowException() {
        when(paymentRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
            paymentService.deletePayment(999)
        );

        verify(paymentRepository).findById(999);
    }

    @Test
    void getPaymentsByPaymentMethod_ShouldReturnFilteredPayments() {
        when(paymentMethodRepository.findById(1)).thenReturn(Optional.of(paymentMethod));
        when(paymentRepository.findPaymentsByPaymentMethod(paymentMethod)).thenReturn(Arrays.asList(payment));

        List<PaymentDto> result = paymentService.getPaymentsByPaymentMethod(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(paymentMethodRepository).findById(1);
        verify(paymentRepository).findPaymentsByPaymentMethod(paymentMethod);
    }

    @Test
    void getPaymentsByPaymentMethod_WhenMethodNotExists_ShouldThrowException() {
        when(paymentMethodRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
            paymentService.getPaymentsByPaymentMethod(999)
        );

        verify(paymentMethodRepository).findById(999);
    }

    @Test
    void getPaymentsByParkingLotId_ShouldReturnFilteredPayments() {
        when(paymentRepository.findPaymentsByParkingLotId(1)).thenReturn(Arrays.asList(payment));

        List<PaymentDto> result = paymentService.getPaymentsByParkingLotId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(paymentRepository).findPaymentsByParkingLotId(1);
    }

    @Test
    void getPaymentsByUserId_ShouldReturnFilteredPayments() {
        when(paymentRepository.findPaymentsByUserId(1)).thenReturn(Arrays.asList(payment));

        List<PaymentDto> result = paymentService.getPaymentsByUserId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(paymentRepository).findPaymentsByUserId(1);
    }
} 