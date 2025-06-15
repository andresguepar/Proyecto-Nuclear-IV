package pn.proyectonuclear4.mapping.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.entity.Slot;
import pn.proyectonuclear4.entity.VehicleType;
import pn.proyectonuclear4.mapping.dto.SlotDto;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SlotMapperTest {

    private Slot slot;
    private ParkingLot parkingLot;
    private VehicleType vehicleType;

    @BeforeEach
    void setUp() {
        // Create related entities
        parkingLot = ParkingLot.builder()
                .idParkingLot(1)
                .name("Downtown Parking")
                .isActive(true)
                .build();

        vehicleType = VehicleType.builder()
                .idVehicleType(1)
                .name("Car")
                .isActive(true)
                .build();

        // Create the main entity
        slot = Slot.builder()
                .idSlot(1)
                .name("A1")
                .isAvailable(true)
                .isActive(true)
                .parkingLot(parkingLot)
                .vehicleType(vehicleType)
                .build();
    }

    @Test
    void mapFrom_EntityToDto_ShouldMapAllFields() {
        // Act
        SlotDto dto = SlotMapper.mapFrom(slot);

        // Assert
        assertNotNull(dto);
        assertEquals(slot.getIdSlot(), dto.idSlot());
        assertEquals(slot.getName(), dto.name());
        assertEquals(slot.getIsAvailable(), dto.isAvailable());
        assertEquals(slot.getIsActive(), dto.isActive());
        assertEquals(slot.getParkingLot(), dto.parkingLot());
        assertEquals(slot.getVehicleType(), dto.vehicleType());
    }

    @Test
    void mapFrom_DtoToEntity_ShouldMapAllFields() {
        // Arrange
        SlotDto dto = SlotMapper.mapFrom(slot);

        // Act
        Slot entity = SlotMapper.mapFrom(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(dto.idSlot(), entity.getIdSlot());
        assertEquals(dto.name(), entity.getName());
        assertEquals(dto.isAvailable(), entity.getIsAvailable());
        assertEquals(dto.isActive(), entity.getIsActive());
        assertEquals(dto.parkingLot(), entity.getParkingLot());
        assertEquals(dto.vehicleType(), entity.getVehicleType());
    }

    @Test
    void mapFrom_EntityListToDtoList_ShouldMapAllEntities() {
        // Arrange
        Slot slot2 = Slot.builder()
                .idSlot(2)
                .name("A2")
                .isAvailable(false)
                .isActive(true)
                .parkingLot(parkingLot)
                .vehicleType(vehicleType)
                .build();

        List<Slot> entities = Arrays.asList(slot, slot2);

        // Act
        List<SlotDto> dtos = SlotMapper.mapFrom(entities);

        // Assert
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(entities.get(0).getIdSlot(), dtos.get(0).idSlot());
        assertEquals(entities.get(1).getIdSlot(), dtos.get(1).idSlot());
    }

    @Test
    void mapToEntities_DtoListToEntityList_ShouldMapAllDtos() {
        // Arrange
        SlotDto dto1 = SlotMapper.mapFrom(slot);
        SlotDto dto2 = new SlotDto(
                2,
                "A2",
                false,
                true,
                parkingLot,
                vehicleType
        );

        List<SlotDto> dtos = Arrays.asList(dto1, dto2);

        // Act
        List<Slot> entities = SlotMapper.mapToEntities(dtos);

        // Assert
        assertNotNull(entities);
        assertEquals(2, entities.size());
        assertEquals(dtos.get(0).idSlot(), entities.get(0).getIdSlot());
        assertEquals(dtos.get(1).idSlot(), entities.get(1).getIdSlot());
    }

    @Test
    void mapFrom_NullEntity_ShouldReturnNull() {
        // Act
        SlotDto dto = SlotMapper.mapFrom((Slot) null);

        // Assert
        assertNull(dto);
    }

    @Test
    void mapFrom_NullDto_ShouldReturnNull() {
        // Act
        Slot entity = SlotMapper.mapFrom((SlotDto) null);

        // Assert
        assertNull(entity);
    }

    @Test
    void mapFrom_NullEntityList_ShouldReturnEmptyList() {
        // Act
        List<SlotDto> dtos = SlotMapper.mapFrom((List<Slot>) null);

        // Assert
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    @Test
    void mapToEntities_NullDtoList_ShouldReturnEmptyList() {
        // Act
        List<Slot> entities = SlotMapper.mapToEntities(null);

        // Assert
        assertNotNull(entities);
        assertTrue(entities.isEmpty());
    }
}
