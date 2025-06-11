package pn.proyectonuclear4.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import pn.proyectonuclear4.entity.Role;
import pn.proyectonuclear4.mapping.dto.UserDto;
import pn.proyectonuclear4.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDto userDto;
    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        role = Role.builder()
                .idRole(1)
                .name("USER")
                .isActive(true)
                .build();
        
        userDto = new UserDto(
            1,
            role,
            "johndoe",
            "password123",
            "john@example.com",
            "123456789",
            "555-0123",
            true
        );
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        // Arrange
        List<UserDto> expectedUsers = Arrays.asList(userDto);
        when(userService.getAllUsers()).thenReturn(expectedUsers);

        // Act
        ResponseEntity<List<UserDto>> response = userController.getAllUsers();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedUsers, response.getBody());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {
        // Arrange
        when(userService.getUserById(1)).thenReturn(Optional.of(userDto));

        // Act
        ResponseEntity<UserDto> response = userController.getUserById(1);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDto, response.getBody());
        verify(userService, times(1)).getUserById(1);
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        when(userService.getUserById(999)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<UserDto> response = userController.getUserById(999);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(userService, times(1)).getUserById(999);
    }

    @Test
    void saveUser_ShouldReturnSavedUser() {
        // Arrange
        when(userService.saveUser(any(UserDto.class))).thenReturn(userDto);

        // Act
        ResponseEntity<UserDto> response = userController.saveUser(userDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDto, response.getBody());
        verify(userService, times(1)).saveUser(userDto);
    }

    @Test
    void deleteUser_WhenUserExists_ShouldReturnSuccessMessage() {
        // Arrange
        doNothing().when(userService).deleteUser(1);

        // Act
        ResponseEntity<String> response = userController.deleteUser(1);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User marked as inactive.", response.getBody());
        verify(userService, times(1)).deleteUser(1);
    }

    @Test
    void deleteUser_WhenUserDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        doThrow(new RuntimeException("User not found")).when(userService).deleteUser(999);

        // Act
        ResponseEntity<String> response = userController.deleteUser(999);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(userService, times(1)).deleteUser(999);
    }

    @Test
    void getUsersByIsActive_ShouldReturnFilteredUsers() {
        // Arrange
        List<UserDto> expectedUsers = Arrays.asList(userDto);
        when(userService.getUsersByIsActive(true)).thenReturn(expectedUsers);

        // Act
        ResponseEntity<List<UserDto>> response = userController.getUsersByIsActive(true);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedUsers, response.getBody());
        verify(userService, times(1)).getUsersByIsActive(true);
    }
} 