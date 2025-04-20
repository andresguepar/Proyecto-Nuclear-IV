package pn.proyectonuclear4.service;

import pn.proyectonuclear4.mapping.dto.UserDto;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> getAllUsers();

    Optional<UserDto> getUserById(int id);

    UserDto saveUser(UserDto userDto);

    void deleteUser(int id);

    List<UserDto> getUsersByIsActive(Boolean isActive);

    UserDto getUserByUsernameAndPassWord(String username, String password);

}
