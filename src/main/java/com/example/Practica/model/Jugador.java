package com.example.Practica.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Jugador {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double saldo;
    private Boolean activo = true;
}