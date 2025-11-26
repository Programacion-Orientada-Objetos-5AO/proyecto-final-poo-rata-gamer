package ar.edu.huergo.rata_gamer.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.rata_gamer.dto.PrestamoLibrosDTO;
import ar.edu.huergo.rata_gamer.entity.Libro;

@Component
public class PrestamoLibrosMapper {
    public PrestamoLibrosDTO toDTO(Libro libro){
        if(libro == null){
            throw new IllegalArgumentException("El precio historico no puede ser nulo");
        }
        return new PrestamoLibrosDTO(
            libro.getId(),
            libro.getTituloLibro(),
            libro.getNombreUsuario(),
            libro.getFechaPrestamo(),
            libro.getFechaDevolucion(),
            libro.getDevuelto());
    }
    public Libro toEntity(PrestamoLibrosDTO prestamoLibrosDTO){
        if (prestamoLibrosDTO == null){
            throw new IllegalArgumentException("La publicacion no puede ser nula");
        }
        return new Libro(
            prestamoLibrosDTO.id(),
            prestamoLibrosDTO.tituloLibro(),
            prestamoLibrosDTO.nombreUsuario(),
            prestamoLibrosDTO.fechaPrestamo(),
            prestamoLibrosDTO.fechaDevolucion(),
            prestamoLibrosDTO.devuelto()
        );
    }
    public List<PrestamoLibrosDTO> toDtoList(List<Libro> libros){
        if(libros == null){
            throw new IllegalArgumentException("La lista de publicaciones no puede ser nula");
        }
        return libros.stream().map(this::toDTO).toList();
    }
}
