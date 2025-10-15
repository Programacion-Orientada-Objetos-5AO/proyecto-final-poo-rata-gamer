package ar.edu.huergo.rata_gamer.mapper.publicaciones;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.rata_gamer.dto.publicaciones.PublicacionDTO;
import ar.edu.huergo.rata_gamer.entity.publicaciones.Publicacion;

@Component
public class PublicacionMapper {
    public PublicacionDTO toDTO(Publicacion publicacion){
        if (publicacion == null){
            throw new IllegalArgumentException("La publicacion no puede ser nula");
        }   
        return new PublicacionDTO(
            publicacion.getId(), 
            publicacion.getJuego(), 
            publicacion.getPlataforma(), 
            publicacion.getPreciosHistoricos());
    }
    public Publicacion toEntity(PublicacionDTO publicacionDTO){
        if (publicacionDTO == null){
            throw new IllegalArgumentException("La publicacion no puede ser nula");
        }
        return new Publicacion(
            publicacionDTO.id(),
            publicacionDTO.juego(),
            publicacionDTO.plataforma(),
            publicacionDTO.preciosHistoricos()
        );
    }
    public List<PublicacionDTO> toDtoList(List<Publicacion> publicaciones){
        if(publicaciones == null){
            throw new IllegalArgumentException("La lista de publicaciones no puede ser nula");
        }
        return publicaciones.stream().map(this::toDTO).toList();
    }
}
