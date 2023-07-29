package com.grupob.ServiMarket.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Table(name = "publicacion")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Publication {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private UserEntity provider;
        
        private String title;

        private String rubro;

        private String description;

        private String urlImagen;

        @OneToMany
        private List<UserEntity> usercliente;

        private boolean confirmacion;// atributo para ver si el trabajo es rechazado/aceptado
        private boolean estado;//atributo para ver si esta en curso o finalizado el trabajo
}
