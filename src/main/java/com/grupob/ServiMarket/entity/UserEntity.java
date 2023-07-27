package com.grupob.ServiMarket.entity;

import com.grupob.ServiMarket.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Table (name = "usuarios")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;


    private String lastName;

    @NotBlank
    @Email
    private String email;


    @NotBlank
    private String password;

    @NotBlank

    private String contact;

    @NotBlank
    private String address;


    private boolean status;//anotacion para dar de baja




    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany
    private List<Publication> servicioContratado;//un usuario contrata servicios y se le asignan

    

}
