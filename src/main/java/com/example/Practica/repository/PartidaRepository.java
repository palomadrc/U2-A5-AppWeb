package com.example.Practica.repository;

import com.example.Practica.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Long> {
    List<Partida> findByJugadorId(Long jugadorId);
}