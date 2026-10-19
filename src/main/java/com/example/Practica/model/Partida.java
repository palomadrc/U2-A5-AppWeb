package com.example.Practica.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Partida {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fecha = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private EstadoPartida estado = EstadoPartida.CREADA;
    private Double apuesta;

    @ManyToOne
    @JoinColumn(name = "jugador_id")
    private Jugador jugador;
}