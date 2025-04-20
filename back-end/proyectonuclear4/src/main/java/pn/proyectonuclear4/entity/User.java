package pn.proyectonuclear4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private String username;

    private String password;

    private String email;

    private String identification;

    private String phone;

    private Boolean isActive;
} 