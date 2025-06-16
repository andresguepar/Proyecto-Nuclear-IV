package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;
import pn.proyectonuclear4.entity.Role;

@Data
@Builder
public class UserDto {
    private int idUser;
    private Role role;
    private String username;
    private String password;
    private String email;
    private String identification;
    private String phone;
    private Boolean isActive;
}
