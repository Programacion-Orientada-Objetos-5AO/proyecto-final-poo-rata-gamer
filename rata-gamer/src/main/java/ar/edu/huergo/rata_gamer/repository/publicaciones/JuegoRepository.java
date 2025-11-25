package ar.edu.huergo.rata_gamer.repository.publicaciones;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.rata_gamer.entity.Publicaciones.Juego;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long>{
    List<Juego> findByNombreContainingIgnoreCase(String nombre);
}