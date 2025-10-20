package ar.edu.huergo.rata_gamer.service.publicaciones;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.rata_gamer.entity.publicaciones.Publicacion;
import ar.edu.huergo.rata_gamer.repository.publicaciones.PublicacionRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PublicacionService {
 
    @Autowired
    private PublicacionRepository publicacionRepository;

    public List<Publicacion> obtenerTodosLasPublicaciones(){
        return publicacionRepository.findAll();
    }

    public Publicacion obtenerPublicacionPorId(Long id) throws EntityNotFoundException{
        return publicacionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Publicacion no encontrada"));
    }

    public Publicacion crearPublicacion(Publicacion publicacion){
        return publicacionRepository.save(publicacion);
    }

    public Publicacion actualizarPublicacion(Publicacion publicacion, Long id){
        Publicacion publicacionACambiar = obtenerPublicacionPorId(id);

        for(Field field : publicacionACambiar.getClass().getDeclaredFields()){
            field.setAccessible(true);

            try{
                if(field.get(publicacion) != null){
                    field.set(publicacionACambiar, field.get(publicacion));
                }
            } catch(IllegalAccessException err){
                err.printStackTrace();
            }
        }
        return publicacionRepository.save(publicacionACambiar);
    }

    public void eliminarPublicacion(Long id){
        Publicacion publicacionAEliminar = obtenerPublicacionPorId(id);
        publicacionRepository.delete(publicacionAEliminar);
    }
}
