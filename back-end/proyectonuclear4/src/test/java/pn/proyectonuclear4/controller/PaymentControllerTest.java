package pn.proyectonuclear4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pn.proyectonuclear4.entity.Fee;
import pn.proyectonuclear4.entity.Payment;
import pn.proyectonuclear4.entity.PaymentMethod;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.PaymentDto;
import pn.proyectonuclear4.mapping.mappers.PaymentMapper;
import pn.proyectonuclear4.service.PaymentService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

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
    void getAllPayments_ShouldReturnListOfPayments() throws Exception {
        List<PaymentDto> payments = Arrays.asList(paymentDto);
        when(paymentService.getAllPayments()).thenReturn(payments);

        mockMvc.perform(get("/payments/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idPayment", is(paymentDto.idPayment())))
                .andExpect(jsonPath("$[0].status", is(paymentDto.status())));

        verify(paymentService).getAllPayments();
    }

    @Test
    void getPaymentById_WhenExists_ShouldReturnPayment() throws Exception {
        when(paymentService.getPaymentById(1)).thenReturn(Optional.of(paymentDto));

        mockMvc.perform(get("/payments/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPayment", is(paymentDto.idPayment())))
                .andExpect(jsonPath("$.status", is(paymentDto.status())));

        verify(paymentService).getPaymentById(1);
    }

    @Test
    void getPaymentById_WhenNotExists_ShouldReturnNotFound() throws Exception {
        when(paymentService.getPaymentById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/payments/get/999"))
                .andExpect(status().isNotFound());

        verify(paymentService).getPaymentById(999);
    }

    @Test
    void savePayment_ShouldReturnSavedPayment() throws Exception {
        when(paymentService.savePayment(any(PaymentDto.class))).thenReturn(paymentDto);

        mockMvc.perform(post("/payments/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPayment", is(paymentDto.idPayment())))
                .andExpect(jsonPath("$.status", is(paymentDto.status())));

        verify(paymentService).savePayment(any(PaymentDto.class));
    }

    @Test
    void deletePayment_WhenExists_ShouldReturnSuccessMessage() throws Exception {
        doNothing().when(paymentService).deletePayment(1);

        mockMvc.perform(delete("/payments/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment status updated to deleted."));

        verify(paymentService).deletePayment(1);
    }

    @Test
    void deletePayment_WhenNotExists_ShouldReturnNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Payment not found"))
                .when(paymentService).deletePayment(999);

        mockMvc.perform(delete("/payments/delete/999"))
                .andExpect(status().isNotFound());

        verify(paymentService).deletePayment(999);
    }

    @Test
    void getPaymentsByPaymentMethod_ShouldReturnListOfPayments() throws Exception {
        List<PaymentDto> payments = Arrays.asList(paymentDto);
        when(paymentService.getPaymentsByPaymentMethod(1)).thenReturn(payments);

        mockMvc.perform(get("/payments/filterByPaymentMethod/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idPayment", is(paymentDto.idPayment())))
                .andExpect(jsonPath("$[0].status", is(paymentDto.status())));

        verify(paymentService).getPaymentsByPaymentMethod(1);
    }

    @Test
    void getPaymentsByParkingLot_ShouldReturnListOfPayments() throws Exception {
        List<PaymentDto> payments = Arrays.asList(paymentDto);
        when(paymentService.getPaymentsByParkingLotId(1)).thenReturn(payments);

        mockMvc.perform(get("/payments/filterByParkingLot/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idPayment", is(paymentDto.idPayment())))
                .andExpect(jsonPath("$[0].status", is(paymentDto.status())));

        verify(paymentService).getPaymentsByParkingLotId(1);
    }

    @Test
    void getPaymentsByUser_ShouldReturnListOfPayments() throws Exception {
        List<PaymentDto> payments = Arrays.asList(paymentDto);
        when(paymentService.getPaymentsByUserId(1)).thenReturn(payments);

        mockMvc.perform(get("/payments/filterByUser/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idPayment", is(paymentDto.idPayment())))
                .andExpect(jsonPath("$[0].status", is(paymentDto.status())));

        verify(paymentService).getPaymentsByUserId(1);
    }
}
