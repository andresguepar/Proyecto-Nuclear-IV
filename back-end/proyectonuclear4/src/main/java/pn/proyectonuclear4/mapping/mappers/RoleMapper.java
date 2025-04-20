package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.Role;
import pn.proyectonuclear4.mapping.dto.RoleDto;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class RoleMapper {
    public static RoleDto mapFrom(Role source) {
        return RoleDto.builder()
                .idRole(source.getIdRole())
                .name(source.getName())
                .isActive(source.getIsActive())
                .build();
    }

    public static Role mapFrom(RoleDto source) {
        return Role.builder()
                .idRole(source.idRole())
                .name(source.name())
                .isActive(source.isActive())
                .build();
    }

    public static List<RoleDto> mapFrom(List<Role> source) {
        return source.stream().map(RoleMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<Role> mapToEntities(List<RoleDto> source) {
        return source.stream().map(RoleMapper::mapFrom).collect(Collectors.toList());
    }
}
