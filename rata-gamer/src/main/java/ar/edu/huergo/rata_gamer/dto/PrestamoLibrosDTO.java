package ar.edu.huergo.rata_gamer.dto;

import java.time.LocalDate;

public record PrestamoLibrosDTO(Long id, String tituloLibro, String nombreUsuario, LocalDate fechaPrestamo, LocalDate fechaDevolucion, Boolean devuelto) {
    
}
