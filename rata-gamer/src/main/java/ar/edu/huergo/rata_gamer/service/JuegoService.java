package ar.edu.huergo.rata_gamer.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.huergo.rata_gamer.entity.Juego;
import ar.edu.huergo.rata_gamer.repository.JuegoRepository;
import jakarta.persistence.EntityNotFoundException;

public class JuegoService {
    @Autowired
    private JuegoRepository juegoRepository;

    public List<Juego> obtenerTodosLosJuegos(){
        return juegoRepository.findAll();
    }
    public Juego obtenerJuegoPorID(Long id) throws EntityNotFoundException{
        return juegoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Juego no encontrado"));
    }
}

