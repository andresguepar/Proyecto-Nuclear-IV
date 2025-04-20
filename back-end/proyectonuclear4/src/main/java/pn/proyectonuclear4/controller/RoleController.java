package pn.proyectonuclear4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pn.proyectonuclear4.exception.ResourceNotFoundException;
import pn.proyectonuclear4.mapping.dto.RoleDto;
import pn.proyectonuclear4.service.RoleService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

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
