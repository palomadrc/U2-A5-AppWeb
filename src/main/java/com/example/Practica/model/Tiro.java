package com.example.Practica.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Tiro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer valorDado1;
    private Integer valorDado2;
    private Integer suma;
    private Boolean esGanador;

    @ManyToOne
    @JoinColumn(name = "partida_id")
    private Partida partida;
}