package ar.edu.huergo.rata_gamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ar.edu.huergo.rata_gamer.entity.Juego;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long>{

}