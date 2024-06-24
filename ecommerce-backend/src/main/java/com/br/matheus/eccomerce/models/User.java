package com.br.matheus.eccomerce.models;

import com.br.matheus.eccomerce.models.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "users")
public class User {

    public User(UserDTO dto) {
        this(dto.getId(), dto.getUsername(), dto.getEmail(), dto.getPassword(), dto.getRoles());
    }

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private String roles;

    public UserDTO toDto() {
        return new UserDTO(id, username, email, password, roles);
    }

}
