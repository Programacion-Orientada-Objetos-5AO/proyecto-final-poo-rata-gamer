package ar.edu.huergo.rata_gamer.controller.publicaciones;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.edu.huergo.rata_gamer.dto.publicaciones.PublicacionDTO;
import ar.edu.huergo.rata_gamer.entity.publicaciones.Publicacion;
import ar.edu.huergo.rata_gamer.mapper.publicaciones.PublicacionMapper;
import ar.edu.huergo.rata_gamer.service.publicaciones.PublicacionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionMapper publicacionMapper;

    @Autowired
    @Qualifier("publicacionServiceService")
    private PublicacionService publicacionService;

    @GetMapping
    public ResponseEntity<List<PublicacionDTO>> obtenerPublicaciones(){
        List<Publicacion> publicaciones = publicacionService.obtenerTodosLasPublicaciones();
        List<PublicacionDTO> publicacionesDTO = publicacionMapper.toDtoList(publicaciones);
        return ResponseEntity.ok(publicacionesDTO);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable Long id){
        Publicacion publicacion = publicacionService.obtenerPublicacionPorId(id);
        PublicacionDTO publicacionDTO = publicacionMapper.toDTO(publicacion);
        return ResponseEntity.ok(publicacionDTO);
    }

    @PostMapping
    public ResponseEntity<PublicacionDTO> crearPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO){
        Publicacion publicacion = publicacionMapper.toEntity(publicacionDTO);
        Publicacion nuevaPublicacion = publicacionService.crearPublicacion(publicacion);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(nuevaPublicacion.getId()).toUri();
        return ResponseEntity.created(location).body(publicacionMapper.toDTO(nuevaPublicacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicacionDTO> actualizarPublicacion(
            @Valid @RequestBody PublicacionDTO publicacionDTO, @PathVariable Long id){
        Publicacion publicacion = publicacionMapper.toEntity(publicacionDTO);
        Publicacion publicacionActualizada = publicacionService.actualizarPublicacion(publicacion, id);
        PublicacionDTO publicacionActualizadaDTO = publicacionMapper.toDTO(publicacionActualizada);
        return ResponseEntity.ok(publicacionActualizadaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPublicacion(@PathVariable Long id){
        publicacionService.eliminarPublicacion(id);
        return ResponseEntity.noContent().build();
    }
}
