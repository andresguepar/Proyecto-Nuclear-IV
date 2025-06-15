package pn.proyectonuclear4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pn.proyectonuclear4.entity.Role;
import pn.proyectonuclear4.mapping.dto.RoleDto;
import pn.proyectonuclear4.mapping.mappers.RoleMapper;
import pn.proyectonuclear4.repository.RoleRepository;
import pn.proyectonuclear4.service.impl.RoleServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;
    private RoleDto roleDto;

    @BeforeEach
    void setUp() {
        role = Role.builder()
                .idRole(1)
                .name("USER")
                .isActive(true)
                .build();

        roleDto = new RoleDto(
            1,
            "USER",
            true
        );
    }

    @Test
    void getAllRoles_ShouldReturnListOfRoles() {
        // Arrange
        List<Role> roles = Arrays.asList(role);
        List<RoleDto> expectedDtos = Arrays.asList(roleDto);
        when(roleRepository.findAll()).thenReturn(roles);
        when(roleMapper.mapFrom(any(Role.class))).thenReturn(roleDto);

        // Act
        List<RoleDto> result = roleService.getAllRoles();

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idRole(), result.get(0).idRole());
        verify(roleRepository).findAll();
        verify(roleMapper).mapFrom(any(Role.class));
    }

    @Test
    void getRoleById_WhenRoleExists_ShouldReturnRole() {
        // Arrange
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));
        when(roleMapper.mapFrom(role)).thenReturn(roleDto);

        // Act
        Optional<RoleDto> result = roleService.getRoleById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(roleDto.idRole(), result.get().idRole());
        assertEquals(roleDto.name(), result.get().name());
        verify(roleRepository).findById(1);
        verify(roleMapper).mapFrom(role);
    }

    @Test
    void getRoleById_WhenRoleDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(roleRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<RoleDto> result = roleService.getRoleById(999);

        // Assert
        assertTrue(result.isEmpty());
        verify(roleRepository).findById(999);
        verify(roleMapper, never()).mapFrom(any(Role.class));
    }

    @Test
    void saveRole_ShouldReturnSavedRole() {
        // Arrange
        when(roleMapper.mapFrom(any(RoleDto.class))).thenReturn(role);
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        when(roleMapper.mapFrom(any(Role.class))).thenReturn(roleDto);

        // Act
        RoleDto result = roleService.saveRole(roleDto);

        // Assert
        assertNotNull(result);
        assertEquals(roleDto.idRole(), result.idRole());
        assertEquals(roleDto.name(), result.name());
        verify(roleMapper).mapFrom(roleDto);
        verify(roleRepository).save(any(Role.class));
        verify(roleMapper).mapFrom(role);
    }

    @Test
    void deleteRole_WhenRoleExists_ShouldMarkAsInactive() {
        // Arrange
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        // Act
        roleService.deleteRole(1);

        // Assert
        verify(roleRepository).findById(1);
        verify(roleRepository).save(any(Role.class));
        assertFalse(role.getIsActive());
    }

    @Test
    void deleteRole_WhenRoleDoesNotExist_ShouldThrowException() {
        // Arrange
        when(roleRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> roleService.deleteRole(999));
        verify(roleRepository).findById(999);
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void getRolesByIsActive_ShouldReturnFilteredRoles() {
        // Arrange
        List<Role> roles = Arrays.asList(role);
        List<RoleDto> expectedDtos = Arrays.asList(roleDto);
        when(roleRepository.findByIsActive(true)).thenReturn(roles);
        when(roleMapper.mapFrom(any(Role.class))).thenReturn(roleDto);

        // Act
        List<RoleDto> result = roleService.getRolesByIsActive(true);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idRole(), result.get(0).idRole());
        verify(roleRepository).findByIsActive(true);
        verify(roleMapper).mapFrom(any(Role.class));
    }
}
