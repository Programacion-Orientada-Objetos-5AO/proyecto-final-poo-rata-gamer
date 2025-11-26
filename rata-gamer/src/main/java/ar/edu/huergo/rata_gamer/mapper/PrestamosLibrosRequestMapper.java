package ar.edu.huergo.rata_gamer.mapper;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.rata_gamer.dto.PrestamoLibrosDTO;
import ar.edu.huergo.rata_gamer.dto.PrestamoLibrosRequestDTO;
import ar.edu.huergo.rata_gamer.entity.Libro;

@Component
public class PrestamosLibrosRequestMapper {

    public Libro toEntity(PrestamoLibrosRequestDTO prestamoLibrosRequestDTO){
        if (prestamoLibrosRequestDTO == null){
            throw new IllegalArgumentException("La publicacion no puede ser nula");
        }
        LocalDate fechaPrestamo = LocalDate.now();
        LocalDate fechaDevolucion = fechaPrestamo.plusDays(prestamoLibrosRequestDTO.diasPrestamo());
        Boolean devuelto = false;
        return new Libro(
            prestamoLibrosRequestDTO.tituloLibro(),
            prestamoLibrosRequestDTO.nombreUsuario(),
            fechaPrestamo,
            fechaDevolucion,
            devuelto
        );
    }
}