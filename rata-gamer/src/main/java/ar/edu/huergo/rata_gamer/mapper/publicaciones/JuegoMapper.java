package ar.edu.huergo.rata_gamer.mapper.publicaciones;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.rata_gamer.dto.publicaciones.JuegoDTO;
import ar.edu.huergo.rata_gamer.entity.Publicaciones.Juego;


@Component
public class JuegoMapper {
    private final PublicacionMapper publicacionMapper;

    public JuegoMapper(PublicacionMapper publicacionMapper) {
        this.publicacionMapper = publicacionMapper;
    }

    public JuegoDTO toDTO(Juego juego){
        if(juego == null){
            throw new IllegalArgumentException("El juego no puede ser nulo");
        }
        return new JuegoDTO(
            juego.getId(),
            juego.getNombre(),
            publicacionMapper.toDtoList(juego.getPublicaciones()));
    }

    public Juego toEntity(JuegoDTO juegoDTO){
        if(juegoDTO == null){
            throw new IllegalArgumentException("El juego no puede ser nulo");
        }
        return new Juego(
            juegoDTO.id(),
            juegoDTO.nombre(),
            publicacionMapper.toEntityList(juegoDTO.publicaciones()));
    }

    public List<JuegoDTO> toDTOList(List<Juego> juegos){
        if(juegos == null){
            throw new IllegalArgumentException("La lista de juegos no puede ser nula");
        }
        return juegos.stream().map(this::toDTO).toList();
    }
}
