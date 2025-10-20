package ar.edu.huergo.rata_gamer.dto.publicaciones;

import java.math.BigDecimal;
import java.time.LocalDate;

import ar.edu.huergo.rata_gamer.entity.publicaciones.Publicacion;

public record PrecioHistoricoDTO(Long id, LocalDate fechaInicio, LocalDate fechaFin, BigDecimal precio, Publicacion publicacion) {
}