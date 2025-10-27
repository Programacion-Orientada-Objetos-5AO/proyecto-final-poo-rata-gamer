package ar.edu.huergo.rata_gamer.service.publicaciones;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.rata_gamer.entity.publicaciones.Plataforma;
import ar.edu.huergo.rata_gamer.repository.publicaciones.PlataformaRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PlataformaService {
    @Autowired
    private PlataformaRepository plataformaRepository;

    public List<Plataforma> obtenerTodasLasPlataformas(){
        return plataformaRepository.findAll();
    }
    public Plataforma obtenerPlataformaPorID(Long id) throws EntityNotFoundException{
        return plataformaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plataforma no encontrada"));
    }

    public Plataforma obtenerPlataformaPorNombre(String nombre) {
        return plataformaRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Plataforma no encontrada"));
    }

    public Plataforma crearPlataforma(Plataforma plataforma) {
        return plataformaRepository.save(plataforma);
    }

    public Plataforma actualizarPlataforma(Plataforma plataforma, Long id){
        Plataforma plataformaACambiar = obtenerPlataformaPorID(id);
        plataformaACambiar.setNombre(plataforma.getNombre());
        return plataformaRepository.save(plataformaACambiar);
    }

    public void eliminarPlataforma(Long id){
        Plataforma plataformaABorrar = obtenerPlataformaPorID(id);
        plataformaRepository.delete(plataformaABorrar); 
    }
}
