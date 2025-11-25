package ar.edu.huergo.rata_gamer.service.publicaciones;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.rata_gamer.entity.Publicaciones.Oferta;
import ar.edu.huergo.rata_gamer.repository.publicaciones.OfertaRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class OfertaService {
    @Autowired
    private OfertaRepository ofertaRepository;

    public List<Oferta> obtenerTodasLasOfertas() {
        return ofertaRepository.findAll();
    }

    public Oferta obtenerOfertaPorId(Long id) throws EntityNotFoundException {
        return ofertaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Oferta no encontrada"));
    }

    public Oferta crearOferta(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    public Oferta actualizarOferta(Oferta oferta, Long id) {
        Oferta ofertaACambiar = obtenerOfertaPorId(id);

        for (Field field : ofertaACambiar.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                if (field.get(oferta) != null) {
                    field.set(ofertaACambiar, field.get(oferta));
                }
            } catch (IllegalAccessException err) {
                err.printStackTrace();
            }
        }
        return ofertaRepository.save(ofertaACambiar);
    }

    public void eliminarOferta(Long id) {
        Oferta ofertaAEliminar = obtenerOfertaPorId(id);
        ofertaRepository.delete(ofertaAEliminar);
    }
}
