package ar.edu.huergo.rata_gamer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.rata_gamer.entity.Libro;


@Repository
public interface PrestamoLibrosRepository extends JpaRepository<Libro, Long>{
    
}
