package pn.proyectonuclear4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pn.proyectonuclear4.entity.User;
import pn.proyectonuclear4.mapping.dto.UserDto;
import pn.proyectonuclear4.mapping.mappers.UserMapper;
import pn.proyectonuclear4.repository.UserRepository;
import pn.proyectonuclear4.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserMapper.mapFrom(users);
    }

    @Override
    public Optional<UserDto> getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserMapper::mapFrom);
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = UserMapper.mapFrom(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.mapFrom(savedUser);
    }

    @Override
    public void deleteUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        user.setIsActive(false);
        userRepository.save(user);
    }

    @Override
    public List<UserDto> getUsersByIsActive(Boolean isActive) {
        List<User> users = userRepository.findByIsActive(isActive);
        return UserMapper.mapFrom(users);
    }

    @Override
    public UserDto getUserByUsernameAndPassWord(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        return UserMapper.mapFrom(user);
    }
}
