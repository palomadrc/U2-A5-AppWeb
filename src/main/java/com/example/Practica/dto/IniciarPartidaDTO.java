package com.example.Practica.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IniciarPartidaDTO {
    @NotNull(message = "El ID del jugador es obligatorio")
    private Long jugadorId;

    @NotNull(message = "La apuesta es obligatoria")
    @Min(value = 1, message = "La apuesta mínima es 1")
    private Double apuesta;
}