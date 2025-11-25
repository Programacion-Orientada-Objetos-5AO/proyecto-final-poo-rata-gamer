package ar.edu.huergo.rata_gamer.service.publicaciones;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.rata_gamer.entity.Publicaciones.PrecioHistorico;
import ar.edu.huergo.rata_gamer.repository.publicaciones.PrecioHistoricoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.lang.reflect.Field;


@Service
public class PrecioHistoricoService {
 
    @Autowired
    private PrecioHistoricoRepository precioHistoricoRepository;

    public List<PrecioHistorico> obtenerTodosLosPrecios(){
        return precioHistoricoRepository.findAll();
    }

    public PrecioHistorico obtenerPrecioHistoricoPorId(Long id) throws EntityNotFoundException{
        return precioHistoricoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("precio historico no encontrado"));
    }

    public PrecioHistorico creaPrecioHistorico(PrecioHistorico precioHistorico){
        if(precioHistorico.getFechaInicio() == null){
            precioHistorico.setFechaInicio(LocalDate.now());
        }
        return precioHistoricoRepository.save(precioHistorico);
    }

    public PrecioHistorico actualizarPrecioHistorico(PrecioHistorico precioHistorico, Long id){
        PrecioHistorico precioACambiar = obtenerPrecioHistoricoPorId(id);
        
        for (Field field : precioACambiar.getClass().getDeclaredFields()) {
            field.setAccessible(true); // permite acceder a campos privados

            try {
                if (field.get(precioHistorico) != null) {
                    field.set(precioACambiar, field.get(precioHistorico));
                }
            } catch (IllegalAccessException err) {
                err.printStackTrace();
            }

        }
        return precioHistoricoRepository.save(precioACambiar);
    }

    public void eliminarPrecioHistoricoPorID(Long id){
        PrecioHistorico precioHistoricoABorrar = obtenerPrecioHistoricoPorId(id);
        precioHistoricoRepository.delete(precioHistoricoABorrar);
    }
}
