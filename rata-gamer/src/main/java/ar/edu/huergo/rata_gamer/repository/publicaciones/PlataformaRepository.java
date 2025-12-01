package ar.edu.huergo.rata_gamer.repository.publicaciones;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.rata_gamer.entity.Publicaciones.Plataforma;

@Repository
public interface PlataformaRepository extends JpaRepository<Plataforma, Long>{
    Optional<Plataforma> findByNombre(String nombre);
}
