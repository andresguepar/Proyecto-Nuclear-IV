package pn.proyectonuclear4.mapping.mappers;

import pn.proyectonuclear4.entity.User;
import pn.proyectonuclear4.mapping.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto mapFrom(User source) {
        return new UserDto(
                source.getIdUser(),
                source.getRole(),
                source.getUsername(),
                source.getPassword(),
                source.getEmail(),
                source.getIdentification(),
                source.getPhone(),
                source.getIsActive()
        );
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
