package pn.proyectonuclear4.mapping.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pn.proyectonuclear4.entity.*;
import pn.proyectonuclear4.mapping.dto.StandardReservationDto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StandardReservationMapperTest {

    private StandardReservation standardReservation;
    private User user;
    private Slot slot;
    private Payment payment;
    private StatusReservation statusReservation;

    @BeforeEach
    void setUp() {
        // Create related entities
        user = User.builder()
                .idUser(1)
                .username("testuser")
                .email("test@example.com")
                .build();

        slot = Slot.builder()
                .idSlot(1)
                .name("A1")
                .isAvailable(true)
                .build();

        payment = Payment.builder()
                .idPayment(1)
                .paymentDate(LocalDateTime.now())
                .build();

        statusReservation = StatusReservation.builder()
                .idStatusReservation(1)
                .name("ACTIVE")
                .isActive(true)
                .build();

        // Create the main entity
        LocalDateTime now = LocalDateTime.now();
        standardReservation = StandardReservation.builder()
                .idStandardReservation(1)
                .slot(slot)
                .user(user)
                .payment(payment)
                .scheduledDateTime(now)
                .checkIn(now.plusHours(1))
                .checkOut(now.plusHours(2))
                .reservationDate(now)
                .plate("ABC123")
                .statusReservation(statusReservation)
                .build();
    }

    @Test
    void mapFrom_EntityToDto_ShouldMapAllFields() {
        // Act
        StandardReservationDto dto = StandardReservationMapper.mapFrom(standardReservation);

        // Assert
        assertNotNull(dto);
        assertEquals(standardReservation.getIdStandardReservation(), dto.idStandardReservation());
        assertEquals(standardReservation.getSlot(), dto.slot());
        assertEquals(standardReservation.getUser(), dto.user());
        assertEquals(standardReservation.getPayment(), dto.payment());
        assertEquals(standardReservation.getScheduledDateTime(), dto.scheduledDateTime());
        assertEquals(standardReservation.getCheckIn(), dto.checkIn());
        assertEquals(standardReservation.getCheckOut(), dto.checkOut());
        assertEquals(standardReservation.getReservationDate(), dto.reservationDate());
        assertEquals(standardReservation.getPlate(), dto.plate());
        assertEquals(standardReservation.getStatusReservation(), dto.statusReservation());
    }

    @Test
    void mapFrom_DtoToEntity_ShouldMapAllFields() {
        // Arrange
        StandardReservationDto dto = StandardReservationMapper.mapFrom(standardReservation);

        // Act
        StandardReservation entity = StandardReservationMapper.mapFrom(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(dto.idStandardReservation(), entity.getIdStandardReservation());
        assertEquals(dto.slot(), entity.getSlot());
        assertEquals(dto.user(), entity.getUser());
        assertEquals(dto.payment(), entity.getPayment());
        assertEquals(dto.scheduledDateTime(), entity.getScheduledDateTime());
        assertEquals(dto.checkIn(), entity.getCheckIn());
        assertEquals(dto.checkOut(), entity.getCheckOut());
        assertEquals(dto.reservationDate(), entity.getReservationDate());
        assertEquals(dto.plate(), entity.getPlate());
        assertEquals(dto.statusReservation(), entity.getStatusReservation());
    }

    @Test
    void mapFrom_EntityListToDtoList_ShouldMapAllEntities() {
        // Arrange
        StandardReservation reservation2 = StandardReservation.builder()
                .idStandardReservation(2)
                .slot(slot)
                .user(user)
                .plate("XYZ789")
                .statusReservation(statusReservation)
                .build();

        List<StandardReservation> entities = Arrays.asList(standardReservation, reservation2);

        // Act
        List<StandardReservationDto> dtos = StandardReservationMapper.mapFrom(entities);

        // Assert
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(entities.get(0).getIdStandardReservation(), dtos.get(0).idStandardReservation());
        assertEquals(entities.get(1).getIdStandardReservation(), dtos.get(1).idStandardReservation());
    }

    @Test
    void mapToEntities_DtoListToEntityList_ShouldMapAllDtos() {
        // Arrange
        StandardReservationDto dto1 = StandardReservationMapper.mapFrom(standardReservation);
        LocalDateTime now = LocalDateTime.now();
        StandardReservationDto dto2 = new StandardReservationDto(
                2,
                slot,
                user,
                null,
                now,
                now.plusHours(1),
                now.plusHours(2),
                now,
                "XYZ789",
                statusReservation
        );

        List<StandardReservationDto> dtos = Arrays.asList(dto1, dto2);

        // Act
        List<StandardReservation> entities = StandardReservationMapper.mapToEntities(dtos);

        // Assert
        assertNotNull(entities);
        assertEquals(2, entities.size());
        assertEquals(dtos.get(0).idStandardReservation(), entities.get(0).getIdStandardReservation());
        assertEquals(dtos.get(1).idStandardReservation(), entities.get(1).getIdStandardReservation());
    }

    @Test
    void mapFrom_NullEntity_ShouldReturnNull() {
        // Act
        StandardReservationDto dto = StandardReservationMapper.mapFrom((StandardReservation) null);

        // Assert
        assertNull(dto);
    }

    @Test
    void mapFrom_NullDto_ShouldReturnNull() {
        // Act
        StandardReservation entity = StandardReservationMapper.mapFrom((StandardReservationDto) null);

        // Assert
        assertNull(entity);
    }

    @Test
    void mapFrom_NullEntityList_ShouldReturnEmptyList() {
        // Act
        List<StandardReservationDto> dtos = StandardReservationMapper.mapFrom((List<StandardReservation>) null);

        // Assert
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    @Test
    void mapToEntities_NullDtoList_ShouldReturnEmptyList() {
        // Act
        List<StandardReservation> entities = StandardReservationMapper.mapToEntities(null);

        // Assert
        assertNotNull(entities);
        assertTrue(entities.isEmpty());
    }
} 