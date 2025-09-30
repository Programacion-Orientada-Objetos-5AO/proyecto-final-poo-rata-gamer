package ar.edu.huergo.rata_gamer.repository.publicaciones;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.rata_gamer.entity.publicaciones.PrecioHistorico;

@Repository
public interface PrecioHistoricoRepository extends JpaRepository<PrecioHistorico, Long>{
}
