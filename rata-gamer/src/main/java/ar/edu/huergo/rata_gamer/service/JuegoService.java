package ar.edu.huergo.rata_gamer.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.rata_gamer.entity.Juego;
import ar.edu.huergo.rata_gamer.repository.JuegoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
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

    public Juego crearJuego(Juego juego) {
        return juegoRepository.save(juego);
    }

    public Juego actualizarJuego(Juego juego, Long id){
        Juego juegoACambiar = obtenerJuegoPorID(id);
        juegoACambiar.setNombre(juego.getNombre());
        return juegoRepository.save(juegoACambiar);
    }

    public void eliminarJuego(Long id){
        Juego juegoABorrar = obtenerJuegoPorID(id);
        juegoRepository.delete(juegoABorrar); 
    }
}