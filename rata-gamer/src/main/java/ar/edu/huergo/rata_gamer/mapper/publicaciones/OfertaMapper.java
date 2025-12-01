package ar.edu.huergo.rata_gamer.mapper.publicaciones;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.rata_gamer.dto.publicaciones.OfertaDTO;
import ar.edu.huergo.rata_gamer.entity.Publicaciones.Oferta;

@Component
public class OfertaMapper {
    private final PublicacionMapper publicacionMapper;

    public OfertaMapper(PublicacionMapper publicacionMapper) {
        this.publicacionMapper = publicacionMapper;
    }

    public OfertaDTO toDTO(Oferta oferta) {
        if (oferta == null) {
            throw new IllegalArgumentException("La oferta no puede ser nula");
        }
        return new OfertaDTO(
            oferta.getId(),
            oferta.getDescripcion(),
            oferta.getDescuento(),
            oferta.getFechaInicio(),
            oferta.getFechaFin(),
            publicacionMapper.toDtoList(oferta.getPublicaciones())
        );
    }

    public Oferta toEntity(OfertaDTO ofertaDTO) {
        if (ofertaDTO == null) {
            throw new IllegalArgumentException("La oferta no puede ser nula");
        }
        return new Oferta(
            ofertaDTO.id(),
            ofertaDTO.descripcion(),
            ofertaDTO.descuento(),
            ofertaDTO.fechaInicio(),
            ofertaDTO.fechaFin(),
            publicacionMapper.toEntityList(ofertaDTO.publicaciones())
        );
    }

    public List<OfertaDTO> toDtoList(List<Oferta> ofertas) {
        if (ofertas == null) {
            throw new IllegalArgumentException("La lista de ofertas no puede ser nula");
        }
        return ofertas.stream().map(this::toDTO).toList();
    }

    public List<Oferta> toEntityList(List<OfertaDTO> ofertaDTOs) {
        if (ofertaDTOs == null) {
            throw new IllegalArgumentException("La lista de ofertas no puede ser nula");
        }
        return ofertaDTOs.stream().map(this::toEntity).toList();
    }
}
