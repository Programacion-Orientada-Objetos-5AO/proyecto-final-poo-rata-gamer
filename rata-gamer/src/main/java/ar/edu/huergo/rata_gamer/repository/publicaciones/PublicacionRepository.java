package ar.edu.huergo.rata_gamer.repository.publicaciones;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.rata_gamer.entity.Publicaciones.Publicacion;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long>{

    
} 
