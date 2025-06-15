package pn.proyectonuclear4.mapping.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pn.proyectonuclear4.entity.ParkingLot;
import pn.proyectonuclear4.entity.Role;
import pn.proyectonuclear4.entity.User;
import pn.proyectonuclear4.mapping.dto.ParkingLotDto;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotMapperTest {

    private ParkingLot parkingLot;
    private User admin;
    private Role adminRole;

    @BeforeEach
    void setUp() {
        // Create related entities
        adminRole = Role.builder()
                .idRole(1)
                .name("ADMIN")
                .isActive(true)
                .build();

        admin = User.builder()
                .idUser(1)
                .username("admin")
                .email("admin@example.com")
                .role(adminRole)
                .isActive(true)
                .build();

        // Create the main entity
        parkingLot = ParkingLot.builder()
                .idParkingLot(1)
                .admin(admin)
                .address("123 Main St")
                .coordX("40.7128")
                .coordY("-74.0060")
                .name("Downtown Parking")
                .nit("123456789")
                .phone("555-0123")
                .isActive(true)
                .build();
    }

    @Test
    void mapFrom_EntityToDto_ShouldMapAllFields() {
        // Act
        ParkingLotDto dto = ParkingLotMapper.mapFrom(parkingLot);

        // Assert
        assertNotNull(dto);
        assertEquals(parkingLot.getIdParkingLot(), dto.idParkingLot());
        assertEquals(parkingLot.getAdmin(), dto.admin());
        assertEquals(parkingLot.getAddress(), dto.address());
        assertEquals(parkingLot.getCoordX(), dto.coordX());
        assertEquals(parkingLot.getCoordY(), dto.coordY());
        assertEquals(parkingLot.getName(), dto.name());
        assertEquals(parkingLot.getNit(), dto.nit());
        assertEquals(parkingLot.getPhone(), dto.phone());
        assertEquals(parkingLot.getIsActive(), dto.isActive());
    }

    @Test
    void mapFrom_DtoToEntity_ShouldMapAllFields() {
        // Arrange
        ParkingLotDto dto = ParkingLotMapper.mapFrom(parkingLot);

        // Act
        ParkingLot entity = ParkingLotMapper.mapFrom(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(dto.idParkingLot(), entity.getIdParkingLot());
        assertEquals(dto.admin(), entity.getAdmin());
        assertEquals(dto.address(), entity.getAddress());
        assertEquals(dto.coordX(), entity.getCoordX());
        assertEquals(dto.coordY(), entity.getCoordY());
        assertEquals(dto.name(), entity.getName());
        assertEquals(dto.nit(), entity.getNit());
        assertEquals(dto.phone(), entity.getPhone());
        assertEquals(dto.isActive(), entity.getIsActive());
    }

    @Test
    void mapFrom_EntityListToDtoList_ShouldMapAllEntities() {
        // Arrange
        ParkingLot parkingLot2 = ParkingLot.builder()
                .idParkingLot(2)
                .admin(admin)
                .address("456 Oak St")
                .coordX("40.7140")
                .coordY("-74.0062")
                .name("Uptown Parking")
                .nit("987654321")
                .phone("555-0124")
                .isActive(true)
                .build();

        List<ParkingLot> entities = Arrays.asList(parkingLot, parkingLot2);

        // Act
        List<ParkingLotDto> dtos = ParkingLotMapper.mapFrom(entities);

        // Assert
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(entities.get(0).getIdParkingLot(), dtos.get(0).idParkingLot());
        assertEquals(entities.get(1).getIdParkingLot(), dtos.get(1).idParkingLot());
    }

    @Test
    void mapToEntities_DtoListToEntityList_ShouldMapAllDtos() {
        // Arrange
        ParkingLotDto dto1 = ParkingLotMapper.mapFrom(parkingLot);
        ParkingLotDto dto2 = new ParkingLotDto(
                2,
                admin,
                "456 Oak St",
                "40.7140",
                "-74.0062",
                "Uptown Parking",
                "987654321",
                "555-0124",
                true
        );

        List<ParkingLotDto> dtos = Arrays.asList(dto1, dto2);

        // Act
        List<ParkingLot> entities = ParkingLotMapper.mapToEntities(dtos);

        // Assert
        assertNotNull(entities);
        assertEquals(2, entities.size());
        assertEquals(dtos.get(0).idParkingLot(), entities.get(0).getIdParkingLot());
        assertEquals(dtos.get(1).idParkingLot(), entities.get(1).getIdParkingLot());
    }

    @Test
    void mapFrom_NullEntity_ShouldReturnNull() {
        // Act
        ParkingLotDto dto = ParkingLotMapper.mapFrom((ParkingLot) null);

        // Assert
        assertNull(dto);
    }

    @Test
    void mapFrom_NullDto_ShouldReturnNull() {
        // Act
        ParkingLot entity = ParkingLotMapper.mapFrom((ParkingLotDto) null);

        // Assert
        assertNull(entity);
    }

    @Test
    void mapFrom_NullEntityList_ShouldReturnEmptyList() {
        // Act
        List<ParkingLotDto> dtos = ParkingLotMapper.mapFrom((List<ParkingLot>) null);

        // Assert
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    @Test
    void mapToEntities_NullDtoList_ShouldReturnEmptyList() {
        // Act
        List<ParkingLot> entities = ParkingLotMapper.mapToEntities(null);

        // Assert
        assertNotNull(entities);
        assertTrue(entities.isEmpty());
    }
}
