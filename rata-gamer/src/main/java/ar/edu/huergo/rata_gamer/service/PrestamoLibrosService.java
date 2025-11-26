package ar.edu.huergo.rata_gamer.service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.rata_gamer.dto.PrestamoLibrosDTO;
import ar.edu.huergo.rata_gamer.entity.Libro;
import ar.edu.huergo.rata_gamer.repository.PrestamoLibrosRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PrestamoLibrosService {

    @Autowired
    private PrestamoLibrosRepository prestamoLibrosRepository;

    public List<Libro> obtenerTodosLosPrestamos(){
        return prestamoLibrosRepository.findAll();
    }

    public Libro obtenerPrestamoPorId(Long id) throws EntityNotFoundException{
        return prestamoLibrosRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("prestamo no encontrado"));
    }

    public Libro crearPrestamoLibro(Libro prestamoLibro){
        return prestamoLibrosRepository.save(prestamoLibro);
    }

    public Libro actualizarPrestamoLibro(Long id, Libro prestamoLibro){
        Libro prestamoLibroACambiar = obtenerPrestamoPorId(id);
        prestamoLibroACambiar.setNombreUsuario(prestamoLibro.getNombreUsuario());
        prestamoLibroACambiar.setTituloLibro(prestamoLibro.getTituloLibro());
        prestamoLibroACambiar.setFechaPrestamo(prestamoLibro.getFechaPrestamo());
        prestamoLibroACambiar.setFechaDevolucion(prestamoLibro.getFechaDevolucion());
        prestamoLibroACambiar.setDevuelto(prestamoLibro.getDevuelto());
        return prestamoLibrosRepository.save(prestamoLibroACambiar);
    }

    public Libro actualizarParcialPrestamoLibro(Long id, Libro prestamoLibro){
        Libro libroACambiar = obtenerPrestamoPorId(id);

        for (Field field : libroACambiar.getClass().getDeclaredFields()) {
            field.setAccessible(true); // permite acceder a campos privados

            try {
                if (field.get(prestamoLibro) != null) {
                    field.set(prestamoLibro, field.get(prestamoLibro));
                }
            } catch (IllegalAccessException err) {
                err.printStackTrace();
            }

        }
        return prestamoLibrosRepository.save(prestamoLibro);

    }

    public void eliminarPrestamoLibro(Long id){
        Libro prestamoLibroABorrar = obtenerPrestamoPorId(id);
        prestamoLibrosRepository.delete(prestamoLibroABorrar);
    }
}
