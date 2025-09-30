package ar.edu.huergo.rata_gamer.dto.publicaciones;

import java.time.LocalDate;

public record PrecioHistoricoDTO(Long id, LocalDate fechaInicio, LocalDate fechaFin, Double precio) {
}