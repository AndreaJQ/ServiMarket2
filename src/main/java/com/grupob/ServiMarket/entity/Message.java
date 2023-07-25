package com.grupob.ServiMarket.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "mensaje")
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private UserEntity provider;

    @ManyToOne
    @JoinColumn(name = "user_client_id")
    private UserEntity userClient;

}
