package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.mapping.dto.RoleDto;
import pn.proyectonuclear4.service.RoleService;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de roles de usuario (Role).
 * Permite crear, consultar, actualizar y eliminar roles,
 * así como filtrarlos por estado.
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

/* Este controlador REST maneja las operaciones relacionadas con los roles en la aplicación. en particular,
permite obtener, guardar y eliminar roles. En el endpoint "/roles/get" se espera que retorne una lista de roles,
tal como el siguiente objeto JSON:

[
  {
    "idRole": 1,
    "name": "basic_user",
    "isActive": true
  },
  {
    "idRole": 2,
    "name": "park_admin",
    "isActive": true
  },
   {
        "idRole": 3,
        "name": "super_admin",
        "isActive": true
    }
]
* */

    @GetMapping("/get")
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable int id) {
        Optional<RoleDto> role = roleService.getRoleById(id);
        return role.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<RoleDto> saveRole(@RequestBody RoleDto roleDto) {
        return ResponseEntity.ok(roleService.saveRole(roleDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable int id) {
        try {
            roleService.deleteRole(id);
            return ResponseEntity.ok("Role status updated to inactive.");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filterByIsActive/{isActive}")
    public ResponseEntity<List<RoleDto>> getRolesByIsActive(@PathVariable Boolean isActive) {
        return ResponseEntity.ok(roleService.getRolesByIsActive(isActive));
    }
}
