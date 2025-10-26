package ar.edu.huergo.rata_gamer.repository.publicaciones;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.rata_gamer.entity.publicaciones.Oferta;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Long> {

}
