package com.example.Practica.service;

import com.example.Practica.dto.IniciarPartidaDTO;
import com.example.Practica.dto.RecargarSaldoDTO;
import com.example.Practica.exeptions.BusinessException;
import com.example.Practica.exeptions.ResourceNotFoundException;
import com.example.Practica.model.EstadoPartida;
import com.example.Practica.model.Jugador;
import com.example.Practica.model.Partida;
import com.example.Practica.model.Tiro;
import com.example.Practica.repository.JugadorRepository;
import com.example.Practica.repository.PartidaRepository;
import com.example.Practica.repository.TiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class JuegoService {

    private final JugadorRepository jugadorRepo;
    private final PartidaRepository partidaRepo;
    private final TiroRepository tiroRepo;
    private final Random random = new Random();

    @Transactional
    public Partida iniciarPartida(IniciarPartidaDTO dto) {
        Jugador jugador = jugadorRepo.findById(dto.getJugadorId())
                .orElseThrow(() -> new ResourceNotFoundException("Jugador no encontrado"));

        if (!jugador.getActivo()) throw new BusinessException("El jugador no está activo");
        if (jugador.getSaldo() < dto.getApuesta()) throw new BusinessException("Saldo insuficiente");

        jugador.setSaldo(jugador.getSaldo() - dto.getApuesta());
        jugadorRepo.save(jugador);

        Partida partida = new Partida();
        partida.setJugador(jugador);
        partida.setApuesta(dto.getApuesta());
        partida.setEstado(EstadoPartida.EN_JUEGO);
        return partidaRepo.save(partida);
    }

    @Transactional
    public Tiro realizarTiro(Long partidaId) {
        Partida partida = partidaRepo.findById(partidaId)
                .orElseThrow(() -> new ResourceNotFoundException("Partida no encontrada"));

        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new BusinessException("La partida no está en juego");
        }

        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        int suma = dado1 + dado2;

        Tiro tiro = new Tiro();
        tiro.setPartida(partida);
        tiro.setValorDado1(dado1);
        tiro.setValorDado2(dado2);
        tiro.setSuma(suma);

        if (suma == 7 || suma == 11) {
            tiro.setEsGanador(true);
            partida.setEstado(EstadoPartida.FINALIZADA);
            Jugador jugador = partida.getJugador();
            jugador.setSaldo(jugador.getSaldo() + (partida.getApuesta() * 2));
            jugadorRepo.save(jugador);
        } else if (suma == 2 || suma == 3 || suma == 12) {
            tiro.setEsGanador(false);
            partida.setEstado(EstadoPartida.FINALIZADA);
        } else {
            tiro.setEsGanador(false);
        }

        partidaRepo.save(partida);
        return tiroRepo.save(tiro);
    }

    @Transactional
    public Partida finalizarPartidaManual(Long partidaId) {
        Partida partida = partidaRepo.findById(partidaId)
                .orElseThrow(() -> new ResourceNotFoundException("Partida no encontrada"));
        partida.setEstado(EstadoPartida.FINALIZADA);
        return partidaRepo.save(partida);
    }

    @Transactional
    public Jugador recargarSaldo(Long jugadorId, RecargarSaldoDTO dto) {
        Jugador jugador = jugadorRepo.findById(jugadorId)
                .orElseThrow(() -> new ResourceNotFoundException("Jugador no encontrado"));
        jugador.setSaldo(jugador.getSaldo() + dto.getMonto());
        return jugadorRepo.save(jugador);
    }

    public List<Partida> obtenerHistorial(Long jugadorId) {
        return partidaRepo.findByJugadorId(jugadorId);
    }
}