package pn.proyectonuclear4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pn.proyectonuclear4.mapping.dto.UserDto;
import pn.proyectonuclear4.entity.Role;
import pn.proyectonuclear4.entity.User;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.mappers.UserMapper;
import pn.proyectonuclear4.repository.UserRepository;
import pn.proyectonuclear4.service.impl.UserServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;
    private Role role;

    @BeforeEach
    void setUp() {
        role = Role.builder()
                .idRole(1)
                .name("USER")
                .isActive(true)
                .build();

        user = User.builder()
                .idUser(1)
                .role(role)
                .username("johndoe")
                .password("password123")
                .email("john@example.com")
                .identification("123456789")
                .phone("555-0123")
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
        List<User> users = Arrays.asList(user);
        List<UserDto> expectedDtos = Arrays.asList(userDto);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.mapFrom(any(User.class))).thenReturn(userDto);

        // Act
        List<UserDto> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idUser(), result.get(0).idUser());
        verify(userRepository).findAll();
        verify(userMapper).mapFrom(any(User.class));
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userMapper.mapFrom(user)).thenReturn(userDto);

        // Act
        Optional<UserDto> result = userService.getUserById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(userDto.idUser(), result.get().idUser());
        assertEquals(userDto.username(), result.get().username());
        verify(userRepository).findById(1);
        verify(userMapper).mapFrom(user);
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<UserDto> result = userService.getUserById(999);

        // Assert
        assertTrue(result.isEmpty());
        verify(userRepository).findById(999);
        verify(userMapper, never()).mapFrom(any(User.class));
    }

    @Test
    void saveUser_ShouldReturnSavedUser() {
        // Arrange
        when(userMapper.mapFrom(any(UserDto.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.mapFrom(any(User.class))).thenReturn(userDto);

        // Act
        UserDto result = userService.saveUser(userDto);

        // Assert
        assertNotNull(result);
        assertEquals(userDto.idUser(), result.idUser());
        assertEquals(userDto.username(), result.username());
        verify(userMapper).mapFrom(userDto);
        verify(userRepository).save(any(User.class));
        verify(userMapper).mapFrom(user);
    }

    @Test
    void deleteUser_WhenUserExists_ShouldDeleteUser() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        userService.deleteUser(1);

        // Assert
        verify(userRepository).findById(1);
        verify(userRepository).save(any(User.class));
        assertFalse(user.getIsActive());
    }

    @Test
    void deleteUser_WhenUserDoesNotExist_ShouldThrowException() {
        // Arrange
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.deleteUser(999));
        verify(userRepository).findById(999);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUsersByIsActive_ShouldReturnFilteredUsers() {
        // Arrange
        List<User> users = Arrays.asList(user);
        List<UserDto> expectedDtos = Arrays.asList(userDto);
        when(userRepository.findByIsActive(true)).thenReturn(users);
        when(userMapper.mapFrom(any(User.class))).thenReturn(userDto);

        // Act
        List<UserDto> result = userService.getUsersByIsActive(true);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).idUser(), result.get(0).idUser());
        verify(userRepository).findByIsActive(true);
        verify(userMapper).mapFrom(any(User.class));
    }

    @Test
    void getUserByUsernameAndPassWord_WhenCredentialsMatch_ShouldReturnUser() {
        // Arrange
        when(userRepository.findByUsernameAndPassword("johndoe", "password123"))
            .thenReturn(user);
        when(userMapper.mapFrom(user)).thenReturn(userDto);

        // Act
        UserDto result = userService.getUserByUsernameAndPassWord("johndoe", "password123");

        // Assert
        assertNotNull(result);
        assertEquals(userDto.idUser(), result.idUser());
        assertEquals(userDto.username(), result.username());
        verify(userRepository).findByUsernameAndPassword("johndoe", "password123");
        verify(userMapper).mapFrom(user);
    }

    @Test
    void getUserByUsernameAndPassWord_WhenCredentialsDoNotMatch_ShouldThrowException() {
        // Arrange
        when(userRepository.findByUsernameAndPassword("wronguser", "wrongpass"))
            .thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, 
            () -> userService.getUserByUsernameAndPassWord("wronguser", "wrongpass"));
        verify(userRepository).findByUsernameAndPassword("wronguser", "wrongpass");
        verify(userMapper, never()).mapFrom(any(User.class));
    }
} 