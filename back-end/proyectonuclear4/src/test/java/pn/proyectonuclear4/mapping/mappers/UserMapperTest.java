package pn.proyectonuclear4.mapping.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pn.proyectonuclear4.entity.Role;
import pn.proyectonuclear4.entity.User;
import pn.proyectonuclear4.mapping.dto.UserDto;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        // Create related entities
        role = Role.builder()
                .idRole(1)
                .name("USER")
                .isActive(true)
                .build();

        // Create the main entity
        user = User.builder()
                .idUser(1)
                .role(role)
                .username("johndoe")
                .password("encryptedPassword123")
                .email("john.doe@example.com")
                .identification("123456789")
                .phone("555-0123")
                .isActive(true)
                .build();
    }

    @Test
    void mapFrom_EntityToDto_ShouldMapAllFields() {
        // Act
        UserDto dto = UserMapper.mapFrom(user);

        // Assert
        assertNotNull(dto);
        assertEquals(user.getIdUser(), dto.idUser());
        assertEquals(user.getRole(), dto.role());
        assertEquals(user.getUsername(), dto.username());
        assertEquals(user.getPassword(), dto.password());
        assertEquals(user.getEmail(), dto.email());
        assertEquals(user.getIdentification(), dto.identification());
        assertEquals(user.getPhone(), dto.phone());
        assertEquals(user.getIsActive(), dto.isActive());
    }

    @Test
    void mapFrom_DtoToEntity_ShouldMapAllFields() {
        // Arrange
        UserDto dto = UserMapper.mapFrom(user);

        // Act
        User entity = UserMapper.mapFrom(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(dto.idUser(), entity.getIdUser());
        assertEquals(dto.role(), entity.getRole());
        assertEquals(dto.username(), entity.getUsername());
        assertEquals(dto.password(), entity.getPassword());
        assertEquals(dto.email(), entity.getEmail());
        assertEquals(dto.identification(), entity.getIdentification());
        assertEquals(dto.phone(), entity.getPhone());
        assertEquals(dto.isActive(), entity.getIsActive());
    }

    @Test
    void mapFrom_EntityListToDtoList_ShouldMapAllEntities() {
        // Arrange
        User user2 = User.builder()
                .idUser(2)
                .role(role)
                .username("janedoe")
                .password("encryptedPassword456")
                .email("jane.doe@example.com")
                .identification("987654321")
                .phone("555-0124")
                .isActive(true)
                .build();

        List<User> entities = Arrays.asList(user, user2);

        // Act
        List<UserDto> dtos = UserMapper.mapFrom(entities);

        // Assert
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(entities.get(0).getIdUser(), dtos.get(0).idUser());
        assertEquals(entities.get(1).getIdUser(), dtos.get(1).idUser());
    }

    @Test
    void mapToEntities_DtoListToEntityList_ShouldMapAllDtos() {
        // Arrange
        UserDto dto1 = UserMapper.mapFrom(user);
        UserDto dto2 = new UserDto(
                2,
                role,
                "janedoe",
                "encryptedPassword456",
                "jane.doe@example.com",
                "987654321",
                "555-0124",
                true
        );

        List<UserDto> dtos = Arrays.asList(dto1, dto2);

        // Act
        List<User> entities = UserMapper.mapToEntities(dtos);

        // Assert
        assertNotNull(entities);
        assertEquals(2, entities.size());
        assertEquals(dtos.get(0).idUser(), entities.get(0).getIdUser());
        assertEquals(dtos.get(1).idUser(), entities.get(1).getIdUser());
    }

    @Test
    void mapFrom_NullEntity_ShouldReturnNull() {
        // Act
        UserDto dto = UserMapper.mapFrom((User) null);

        // Assert
        assertNull(dto);
    }

    @Test
    void mapFrom_NullDto_ShouldReturnNull() {
        // Act
        User entity = UserMapper.mapFrom((UserDto) null);

        // Assert
        assertNull(entity);
    }

    @Test
    void mapFrom_NullEntityList_ShouldReturnEmptyList() {
        // Act
        List<UserDto> dtos = UserMapper.mapFrom((List<User>) null);

        // Assert
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    @Test
    void mapToEntities_NullDtoList_ShouldReturnEmptyList() {
        // Act
        List<User> entities = UserMapper.mapToEntities(null);

        // Assert
        assertNotNull(entities);
        assertTrue(entities.isEmpty());
    }
}
