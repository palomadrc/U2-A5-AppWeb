package com.example.Practica.controller;

import com.example.Practica.dto.IniciarPartidaDTO;
import com.example.Practica.dto.RecargarSaldoDTO;
import com.example.Practica.service.JuegoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/juego")
@RequiredArgsConstructor
public class JuegoController {

    private final JuegoService juegoService;

    @PostMapping("/iniciar")
    public ResponseEntity<?> iniciarPartida(@Valid @RequestBody IniciarPartidaDTO dto) {
        return ResponseEntity.ok(juegoService.iniciarPartida(dto));
    }

    @PostMapping("/partida/{id}/tiro")
    public ResponseEntity<?> realizarTiro(@PathVariable Long id) {
        return ResponseEntity.ok(juegoService.realizarTiro(id));
    }

    @PostMapping("/partida/{id}/finalizar")
    public ResponseEntity<?> finalizarPartida(@PathVariable Long id) {
        return ResponseEntity.ok(juegoService.finalizarPartidaManual(id));
    }

    @PostMapping("/jugador/{id}/recargar")
    public ResponseEntity<?> recargarSaldo(@PathVariable Long id, @Valid @RequestBody RecargarSaldoDTO dto) {
        return ResponseEntity.ok(juegoService.recargarSaldo(id, dto));
    }

    @GetMapping("/jugador/{id}/historial")
    public ResponseEntity<?> obtenerHistorial(@PathVariable Long id) {
        return ResponseEntity.ok(juegoService.obtenerHistorial(id));
    }
}