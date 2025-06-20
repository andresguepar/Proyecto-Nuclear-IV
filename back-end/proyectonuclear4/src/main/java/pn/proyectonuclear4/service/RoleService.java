package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.RoleDto;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<RoleDto> getAllRoles();

    Optional<RoleDto> getRoleById(int id);

    RoleDto saveRole(RoleDto roleDto);

    void deleteRole(int id);

    List<RoleDto> getRolesByIsActive(Boolean isActive);

}
