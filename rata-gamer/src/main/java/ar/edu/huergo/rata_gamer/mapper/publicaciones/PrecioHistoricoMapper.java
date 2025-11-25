package ar.edu.huergo.rata_gamer.mapper.publicaciones;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.rata_gamer.dto.publicaciones.PrecioHistoricoDTO;
import ar.edu.huergo.rata_gamer.entity.Publicaciones.PrecioHistorico;


@Component
public class PrecioHistoricoMapper {
    public PrecioHistoricoDTO toDTO(PrecioHistorico precioHistorico){
        if(precioHistorico == null){
            throw new IllegalArgumentException("El precio historico no puede ser nulo");
        }
        return new PrecioHistoricoDTO(
            precioHistorico.getId(), 
            precioHistorico.getFechaInicio(),
            precioHistorico.getFechaFin(),
            precioHistorico.getPrecio(),
            precioHistorico.getPublicacion());
    }

    public PrecioHistorico toEntity(PrecioHistoricoDTO precioHistoricoDTO){
        if(precioHistoricoDTO == null){
            throw new IllegalArgumentException("El precio historico no puede ser nulo");
        }
        return new PrecioHistorico(
            precioHistoricoDTO.id(), 
            precioHistoricoDTO.fechaInicio(),
            precioHistoricoDTO.fechaFin(),
            precioHistoricoDTO.precio(),
            precioHistoricoDTO.publicacion());
    }

    public List<PrecioHistoricoDTO> toDTOList(List<PrecioHistorico> precioHistoricos){
        if(precioHistoricos == null){
            throw new IllegalArgumentException("La lista de precioHistoricos no puede ser nula");
        }
        return precioHistoricos.stream().map(this::toDTO).toList();
    }
}

