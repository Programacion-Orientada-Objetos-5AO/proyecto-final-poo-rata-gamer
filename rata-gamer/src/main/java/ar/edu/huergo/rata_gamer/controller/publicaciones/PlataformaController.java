package ar.edu.huergo.rata_gamer.controller.publicaciones;

import java.net.URI;
import java.util.List;

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

import ar.edu.huergo.rata_gamer.dto.publicaciones.PlataformaDTO;
import ar.edu.huergo.rata_gamer.entity.publicaciones.Plataforma;
import ar.edu.huergo.rata_gamer.mapper.publicaciones.PlataformaMapper;
import ar.edu.huergo.rata_gamer.service.publicaciones.PlataformaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/plataformas")
public class PlataformaController {
    @Autowired
    private PlataformaService plataformaService;

    @Autowired
    private PlataformaMapper plataformaMapper;

    @GetMapping
    public ResponseEntity<List<PlataformaDTO>> obtenerPlataformas() {
        List<Plataforma> plataformas = plataformaService.obtenerTodasLasPlataformas();
        List<PlataformaDTO> plataformasDTO = plataformaMapper.toDTOList(plataformas);
        return ResponseEntity.ok(plataformasDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlataformaDTO> obtenerPlataformaPorId(@PathVariable Long id) {
        Plataforma plataforma = plataformaService.obtenerPlataformaPorID(id);
        PlataformaDTO plataformaDTO = plataformaMapper.toDTO(plataforma);
        return ResponseEntity.ok(plataformaDTO);
    }

    @PostMapping
    public ResponseEntity<PlataformaDTO> crearPlataforma(@Valid @RequestBody PlataformaDTO plataformaDTO) {
        Plataforma plataforma = plataformaMapper.toEntity(plataformaDTO);
        Plataforma nuevaPlataforma = plataformaService.crearPlataforma(plataforma);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(nuevaPlataforma.getId()).toUri();
        return ResponseEntity.created(location).body(plataformaMapper.toDTO(nuevaPlataforma));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlataformaDTO> actualizarPlataforma(@PathVariable Long id, @Valid @RequestBody PlataformaDTO plataformaDTO) {
        Plataforma plataforma = plataformaMapper.toEntity(plataformaDTO);
        Plataforma plataformaActualizada = plataformaService.actualizarPlataforma(plataforma, id);
        PlataformaDTO plataformaActualizadaDTO = plataformaMapper.toDTO(plataformaActualizada);
        return ResponseEntity.ok(plataformaActualizadaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlataforma(@Valid @PathVariable Long id) {
        plataformaService.eliminarPlataforma(id);
        return ResponseEntity.noContent().build();
    }
}
