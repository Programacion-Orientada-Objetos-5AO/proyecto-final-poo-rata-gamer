package ar.edu.huergo.rata_gamer.dto.publicaciones;

import java.time.LocalDate;
import java.util.List;

public record OfertaDTO(Long id, String descripcion, Double descuento, LocalDate fechaInicio, LocalDate fechaFin, List<PublicacionDTO> publicaciones) {
}
