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

import ar.edu.huergo.rata_gamer.dto.publicaciones.OfertaDTO;
import ar.edu.huergo.rata_gamer.entity.publicaciones.Oferta;
import ar.edu.huergo.rata_gamer.mapper.publicaciones.OfertaMapper;
import ar.edu.huergo.rata_gamer.service.publicaciones.OfertaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ofertas")
public class OfertaController {
    @Autowired
    private OfertaService ofertaService;

    @Autowired
    private OfertaMapper ofertaMapper;

    @GetMapping
    public ResponseEntity<List<OfertaDTO>> obtenerOfertas() {
        List<Oferta> ofertas = ofertaService.obtenerTodasLasOfertas();
        List<OfertaDTO> ofertasDTO = ofertaMapper.toDtoList(ofertas);
        return ResponseEntity.ok(ofertasDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfertaDTO> obtenerOfertaPorId(@PathVariable Long id) {
        Oferta oferta = ofertaService.obtenerOfertaPorId(id);
        OfertaDTO ofertaDTO = ofertaMapper.toDTO(oferta);
        return ResponseEntity.ok(ofertaDTO);
    }

    @PostMapping
    public ResponseEntity<OfertaDTO> crearOferta(@Valid @RequestBody OfertaDTO ofertaDTO) {
        Oferta oferta = ofertaMapper.toEntity(ofertaDTO);
        Oferta nuevaOferta = ofertaService.crearOferta(oferta);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(nuevaOferta.getId()).toUri();
        return ResponseEntity.created(location).body(ofertaMapper.toDTO(nuevaOferta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfertaDTO> actualizarOferta(@PathVariable Long id, @Valid @RequestBody OfertaDTO ofertaDTO) {
        Oferta oferta = ofertaMapper.toEntity(ofertaDTO);
        Oferta ofertaActualizada = ofertaService.actualizarOferta(oferta, id);
        OfertaDTO ofertaActualizadaDTO = ofertaMapper.toDTO(ofertaActualizada);
        return ResponseEntity.ok(ofertaActualizadaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOferta(@Valid @PathVariable Long id) {
        ofertaService.eliminarOferta(id);
        return ResponseEntity.noContent().build();
    }
}
