package ar.edu.huergo.rata_gamer.mapper.publicaciones;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.rata_gamer.dto.publicaciones.PlataformaDTO;
import ar.edu.huergo.rata_gamer.entity.publicaciones.Plataforma;

@Component
public class PlataformaMapper {
    private final PublicacionMapper publicacionMapper;

    public PlataformaMapper(PublicacionMapper publicacionMapper) {
        this.publicacionMapper = publicacionMapper;
    }

    public PlataformaDTO toDTO(Plataforma plataforma) {
        if (plataforma == null) {
            throw new IllegalArgumentException("La plataforma no puede ser nula");
        }
        return new PlataformaDTO(
            plataforma.getId(),
            plataforma.getNombre(),
            publicacionMapper.toDtoList(plataforma.getPublicaciones())
        );
    }

    public Plataforma toEntity(PlataformaDTO plataformaDTO) {
        if (plataformaDTO == null) {
            throw new IllegalArgumentException("La plataforma no puede ser nula");
        }
        return new Plataforma(
            plataformaDTO.id(),
            plataformaDTO.nombre(),
            publicacionMapper.toEntityList(plataformaDTO.publicaciones())
        );
    }

    public List<PlataformaDTO> toDTOList(List<Plataforma> plataformas) {
        if (plataformas == null) {
            throw new IllegalArgumentException("La lista de plataformas no puede ser nula");
        }
        return plataformas.stream().map(this::toDTO).toList();
    }
}
