package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.User;
import pn.proyectonuclear4.mapping.dto.UserDto;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class UserMapper {
    public static UserDto mapFrom(User source) {
        return UserDto.builder()
                .idUser(source.getIdUser())
                .role(source.getRole())
                .username(source.getUsername())
                .password(source.getPassword())
                .email(source.getEmail())
                .identification(source.getIdentification())
                .phone(source.getPhone())
                .isActive(source.getIsActive())
                .build();
    }

    public static User mapFrom(UserDto source) {
        return User.builder()
                .idUser(source.idUser())
                .role(source.role())
                .username(source.username())
                .password(source.password())
                .email(source.email())
                .identification(source.identification())
                .phone(source.phone())
                .isActive(source.isActive())
                .build();
    }

    public static List<UserDto> mapFrom(List<User> source) {
        return source.stream().map(UserMapper::mapFrom).collect(Collectors.toList());
    }

    public static List<User> mapToEntities(List<UserDto> source) {
        return source.stream().map(UserMapper::mapFrom).collect(Collectors.toList());
    }
}
