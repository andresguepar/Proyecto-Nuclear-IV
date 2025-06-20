package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.proyectonuclear4.entity.Role;
import pn.proyectonuclear4.mapping.dto.RoleDto;
import pn.proyectonuclear4.mapping.mappers.RoleMapper;
import pn.proyectonuclear4.repository.RoleRepository;
import pn.proyectonuclear4.service.RoleService;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return RoleMapper.mapFrom(roles);
    }

    @Override
    public Optional<RoleDto> getRoleById(int id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.map(RoleMapper::mapFrom);
    }

    @Override
    public RoleDto saveRole(RoleDto roleDto) {
        Role role = RoleMapper.mapFrom(roleDto);
        Role savedRole = roleRepository.save(role);
        return RoleMapper.mapFrom(savedRole);
    }

    @Override
    public void deleteRole(int id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No role found with id " + id));
        role.setIsActive(false);
        roleRepository.save(role);
    }

    @Override
    public List<RoleDto> getRolesByIsActive(Boolean isActive) {
        List<Role> roles = roleRepository.findByIsActive(isActive);
        return RoleMapper.mapFrom(roles);
    }
}
