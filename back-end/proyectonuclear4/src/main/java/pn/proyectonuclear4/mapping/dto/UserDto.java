package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import pn.proyectonuclear4.entity.Role;

@Builder
public record UserDto(
        int idUser,
        Role role,
        String username,
        String password,
        String email,
        String identification,
        String phone,
        Boolean isActive
) {
}
