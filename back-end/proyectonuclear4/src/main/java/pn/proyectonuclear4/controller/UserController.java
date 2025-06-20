package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.mapping.dto.UserDto;
import pn.proyectonuclear4.service.UserService;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de usuarios (User).
 * Permite crear, consultar, actualizar y eliminar usuarios,
 * así como autenticarlos y filtrarlos por estado o rol.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
        Optional<UserDto> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.saveUser(userDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User marked as inactive.");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/is-active/{isActive}")
    public ResponseEntity<List<UserDto>> getUsersByIsActive(@PathVariable Boolean isActive) {
        return ResponseEntity.ok(userService.getUsersByIsActive(isActive));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserDto> authenticateUser(@RequestParam String username, @RequestParam String password) {
        UserDto userDto = userService.getUserByUsernameAndPassWord(username, password);
        return userDto != null ? ResponseEntity.ok(userDto) : ResponseEntity.notFound().build();
    }
}
