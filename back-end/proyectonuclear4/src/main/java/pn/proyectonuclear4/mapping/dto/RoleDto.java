package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;

@Builder
public record RoleDto(
        int idRole,
        String name,
        Boolean isActive
) {
}
