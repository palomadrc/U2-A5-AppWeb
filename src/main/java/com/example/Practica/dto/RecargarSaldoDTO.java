package com.example.Practica.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RecargarSaldoDTO {
    @NotNull
    @Min(1)
    private Double monto;
}