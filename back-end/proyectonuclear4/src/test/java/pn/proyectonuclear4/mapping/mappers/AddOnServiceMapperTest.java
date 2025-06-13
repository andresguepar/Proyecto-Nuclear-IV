package pn.proyectonuclear4.mapping.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pn.proyectonuclear4.entity.*;
import pn.proyectonuclear4.mapping.dto.AddOnServiceDto;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddOnServiceMapperTest {

    private AddOnService addOnService;
    private ParkingLot parkingLot;

    @BeforeEach
    void setUp() {
        // Create related entities
        parkingLot = ParkingLot.builder()
                .idParkingLot(1)
                .name("Downtown Parking")
                .isActive(true)
                .build();

        // Create the main entity
        addOnService = AddOnService.builder()
                .idAddOnService(1)
                .name("Car Wash")
                .description("Professional car washing service")
                .price(25.0)
                .isActive(true)
                .parkingLot(parkingLot)
                .build();
    }

    @Test
    void mapFrom_EntityToDto_ShouldMapAllFields() {
        // Act
        AddOnServiceDto dto = AddOnServiceMapper.mapFrom(addOnService);

        // Assert
        assertNotNull(dto);
        assertEquals(addOnService.getIdAddOnService(), dto.idAddOnService());
        assertEquals(addOnService.getName(), dto.name());
        assertEquals(addOnService.getDescription(), dto.description());
        assertEquals(addOnService.getPrice(), dto.price());
        assertEquals(addOnService.getIsActive(), dto.isActive());
        assertEquals(addOnService.getParkingLot(), dto.parkingLot());
    }

    @Test
    void mapFrom_DtoToEntity_ShouldMapAllFields() {
        // Arrange
        AddOnServiceDto dto = AddOnServiceMapper.mapFrom(addOnService);

        // Act
        AddOnService entity = AddOnServiceMapper.mapFrom(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(dto.idAddOnService(), entity.getIdAddOnService());
        assertEquals(dto.name(), entity.getName());
        assertEquals(dto.description(), entity.getDescription());
        assertEquals(dto.price(), entity.getPrice());
        assertEquals(dto.isActive(), entity.getIsActive());
        assertEquals(dto.parkingLot(), entity.getParkingLot());
    }

    @Test
    void mapFrom_EntityListToDtoList_ShouldMapAllEntities() {
        // Arrange
        AddOnService addOnService2 = AddOnService.builder()
                .idAddOnService(2)
                .name("Valet Parking")
                .description("Professional valet parking service")
                .price(15.0)
                .isActive(true)
                .parkingLot(parkingLot)
                .build();

        List<AddOnService> entities = Arrays.asList(addOnService, addOnService2);

        // Act
        List<AddOnServiceDto> dtos = AddOnServiceMapper.mapFrom(entities);

        // Assert
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(entities.get(0).getIdAddOnService(), dtos.get(0).idAddOnService());
        assertEquals(entities.get(1).getIdAddOnService(), dtos.get(1).idAddOnService());
    }

    @Test
    void mapToEntities_DtoListToEntityList_ShouldMapAllDtos() {
        // Arrange
        AddOnServiceDto dto1 = AddOnServiceMapper.mapFrom(addOnService);
        AddOnServiceDto dto2 = new AddOnServiceDto(
                2,
                "Valet Parking",
                "Professional valet parking service",
                15.0,
                true,
                parkingLot
        );

        List<AddOnServiceDto> dtos = Arrays.asList(dto1, dto2);

        // Act
        List<AddOnService> entities = AddOnServiceMapper.mapToEntities(dtos);

        // Assert
        assertNotNull(entities);
        assertEquals(2, entities.size());
        assertEquals(dtos.get(0).idAddOnService(), entities.get(0).getIdAddOnService());
        assertEquals(dtos.get(1).idAddOnService(), entities.get(1).getIdAddOnService());
    }

    @Test
    void mapFrom_NullEntity_ShouldReturnNull() {
        // Act
        AddOnServiceDto dto = AddOnServiceMapper.mapFrom((AddOnService) null);

        // Assert
        assertNull(dto);
    }

    @Test
    void mapFrom_NullDto_ShouldReturnNull() {
        // Act
        AddOnService entity = AddOnServiceMapper.mapFrom((AddOnServiceDto) null);

        // Assert
        assertNull(entity);
    }

    @Test
    void mapFrom_NullEntityList_ShouldReturnEmptyList() {
        // Act
        List<AddOnServiceDto> dtos = AddOnServiceMapper.mapFrom((List<AddOnService>) null);

        // Assert
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    @Test
    void mapToEntities_NullDtoList_ShouldReturnEmptyList() {
        // Act
        List<AddOnService> entities = AddOnServiceMapper.mapToEntities(null);

        // Assert
        assertNotNull(entities);
        assertTrue(entities.isEmpty());
    }
} 