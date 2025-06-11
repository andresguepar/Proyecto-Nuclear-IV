package pn.proyectonuclear4.mapping.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pn.proyectonuclear4.entity.*;
import pn.proyectonuclear4.mapping.dto.PaymentDto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMapperTest {

    private Payment payment;
    private PaymentMethod paymentMethod;
    private Fee fee;

    @BeforeEach
    void setUp() {
        // Create related entities
        paymentMethod = PaymentMethod.builder()
                .idPaymentMethod(1)
                .name("Credit Card")
                .isActive(true)
                .build();

        fee = Fee.builder()
                .idFee(1)
                .name("Standard Fee")
                .description("Standard parking fee")
                .total(10.0)
                .build();

        // Create the main entity
        payment = Payment.builder()
                .idPayment(1)
                .paymentDate(LocalDateTime.now())
                .status("PAID")
                .paymentMethod(paymentMethod)
                .fee(fee)
                .build();
    }

    @Test
    void mapFrom_EntityToDto_ShouldMapAllFields() {
        // Act
        PaymentDto dto = PaymentMapper.mapFrom(payment);

        // Assert
        assertNotNull(dto);
        assertEquals(payment.getIdPayment(), dto.idPayment());
        assertEquals(payment.getPaymentDate(), dto.paymentDate());
        assertEquals(payment.getStatus(), dto.status());
        assertEquals(payment.getPaymentMethod(), dto.paymentMethod());
        assertEquals(payment.getFee(), dto.fee());
    }

    @Test
    void mapFrom_DtoToEntity_ShouldMapAllFields() {
        // Arrange
        PaymentDto dto = PaymentMapper.mapFrom(payment);

        // Act
        Payment entity = PaymentMapper.mapFrom(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(dto.idPayment(), entity.getIdPayment());
        assertEquals(dto.paymentDate(), entity.getPaymentDate());
        assertEquals(dto.status(), entity.getStatus());
        assertEquals(dto.paymentMethod(), entity.getPaymentMethod());
        assertEquals(dto.fee(), entity.getFee());
    }

    @Test
    void mapFrom_EntityListToDtoList_ShouldMapAllEntities() {
        // Arrange
        Payment payment2 = Payment.builder()
                .idPayment(2)
                .paymentDate(LocalDateTime.now())
                .status("PENDING")
                .paymentMethod(paymentMethod)
                .build();

        List<Payment> entities = Arrays.asList(payment, payment2);

        // Act
        List<PaymentDto> dtos = PaymentMapper.mapFrom(entities);

        // Assert
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(entities.get(0).getIdPayment(), dtos.get(0).idPayment());
        assertEquals(entities.get(1).getIdPayment(), dtos.get(1).idPayment());
    }

    @Test
    void mapToEntities_DtoListToEntityList_ShouldMapAllDtos() {
        // Arrange
        PaymentDto dto1 = PaymentMapper.mapFrom(payment);
        LocalDateTime now = LocalDateTime.now();
        PaymentDto dto2 = new PaymentDto(
                2,
                now,
                "PENDING",
                paymentMethod,
                null
        );

        List<PaymentDto> dtos = Arrays.asList(dto1, dto2);

        // Act
        List<Payment> entities = PaymentMapper.mapToEntities(dtos);

        // Assert
        assertNotNull(entities);
        assertEquals(2, entities.size());
        assertEquals(dtos.get(0).idPayment(), entities.get(0).getIdPayment());
        assertEquals(dtos.get(1).idPayment(), entities.get(1).getIdPayment());
    }

    @Test
    void mapFrom_NullEntity_ShouldReturnNull() {
        // Act
        PaymentDto dto = PaymentMapper.mapFrom((Payment) null);

        // Assert
        assertNull(dto);
    }

    @Test
    void mapFrom_NullDto_ShouldReturnNull() {
        // Act
        Payment entity = PaymentMapper.mapFrom((PaymentDto) null);

        // Assert
        assertNull(entity);
    }

    @Test
    void mapFrom_NullEntityList_ShouldReturnEmptyList() {
        // Act
        List<PaymentDto> dtos = PaymentMapper.mapFrom((List<Payment>) null);

        // Assert
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    @Test
    void mapToEntities_NullDtoList_ShouldReturnEmptyList() {
        // Act
        List<Payment> entities = PaymentMapper.mapToEntities(null);

        // Assert
        assertNotNull(entities);
        assertTrue(entities.isEmpty());
    }
} 