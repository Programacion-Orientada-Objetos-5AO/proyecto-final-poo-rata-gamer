package ar.edu.huergo.rata_gamer.mapper.publicaciones;
import java.util.List;

import org.springframework.stereotype.Component;

<<<<<<< HEAD:rata-gamer/src/main/java/ar/edu/huergo/rata_gamer/mapper/JuegoMapper.java
import ar.edu.huergo.rata_gamer.dto.JuegoDTO;
=======
import ar.edu.huergo.rata_gamer.dto.publicaciones.JuegoDTO;
>>>>>>> parent of c02589a (Revert "Merge branch 'feat/Juegos' of https://github.com/Programacion-Orientada-Objetos-5AO/proyecto-final-poo-rata-gamer into feat/Juegos"):rata-gamer/src/main/java/ar/edu/huergo/rata_gamer/mapper/publicaciones/JuegoMapper.java
import ar.edu.huergo.rata_gamer.entity.publicaciones.Juego;


@Component
public class JuegoMapper {
    public JuegoDTO toDTO(Juego juego){
        if(juego == null){
            throw new IllegalArgumentException("El juego no puede ser nulo");
        }
        return new JuegoDTO(
            juego.getId(), 
            juego.getNombre());
    }

    public Juego toEntity(JuegoDTO juegoDTO){
        if(juegoDTO == null){
            throw new IllegalArgumentException("El juego no puede ser nulo");
        }
        return new Juego(
            juegoDTO.id(), 
            juegoDTO.nombre());
    }

    public List<JuegoDTO> toDTOList(List<Juego> juegos){
        if(juegos == null){
            throw new IllegalArgumentException("La lista de juegos no puede ser nula");
        }
        return juegos.stream().map(this::toDTO).toList();
    }
}
