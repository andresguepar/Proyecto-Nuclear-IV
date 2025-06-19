package pn.proyectonuclear4.mapping.dto;

import pn.proyectonuclear4.entity.Role;

public record UserDto(
    int idUser,
    Role role,
    String username,
    String password,
    String email,
    String identification,
    String phone,
    Boolean isActive
) {}
