package ar.edu.huergo.rata_gamer.repository.publicaciones;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.rata_gamer.entity.Publicaciones.PrecioHistorico;
import ar.edu.huergo.rata_gamer.entity.Publicaciones.Publicacion;

@Repository
public interface PrecioHistoricoRepository extends JpaRepository<PrecioHistorico, Long>{
    List<PrecioHistorico> findByPublicacion(Publicacion publicacion);
}
