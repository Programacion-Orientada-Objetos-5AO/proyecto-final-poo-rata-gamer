package ar.edu.huergo.rata_gamer.dto.publicaciones;

import java.util.List;

public record PlataformaDTO(Long id, String nombre, List<PublicacionDTO> publicaciones) {
}
