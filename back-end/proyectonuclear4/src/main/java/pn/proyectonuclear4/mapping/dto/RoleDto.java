package pn.proyectonuclear4.mapping.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto{
        private int idRole;
        private String name;
        private Boolean isActive;
}
