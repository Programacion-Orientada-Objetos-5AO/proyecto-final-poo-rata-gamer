package ar.edu.huergo.rata_gamer.controller.publicaciones;

import java.net.URI;
import java.util.List;

import org.apache.catalina.connector.Response;
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

import ar.edu.huergo.rata_gamer.dto.publicaciones.JuegoDTO;
import ar.edu.huergo.rata_gamer.dto.publicaciones.PrecioHistoricoDTO;
import ar.edu.huergo.rata_gamer.entity.publicaciones.PrecioHistorico;
import ar.edu.huergo.rata_gamer.mapper.publicaciones.PrecioHistoricoMapper;
import ar.edu.huergo.rata_gamer.service.publicaciones.PrecioHistoricoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/precios-historicos")
public class PrecioHistoricoController {
    @Autowired
    private PrecioHistoricoMapper precioHistoricoMapper;

    @Autowired
    private PrecioHistoricoService precioHistoricoService;

    @GetMapping
    public ResponseEntity<List<PrecioHistoricoDTO>> obtenerPreciosHistoricos(){
        List<PrecioHistorico> precioHistoricos = precioHistoricoService.obtenerTodosLosPrecios();
        List<PrecioHistoricoDTO> precioHistoricoDTOs = precioHistoricoMapper.toDTOList(precioHistoricos);
        return ResponseEntity.ok(precioHistoricoDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrecioHistoricoDTO> obtenerPrecioHistoricoPorID(Long id){
        PrecioHistorico precioHistorico = precioHistoricoService.obtenerPrecioHistoricoPorId(id);
        PrecioHistoricoDTO precioHistoricoDTO = precioHistoricoMapper.toDTO(precioHistorico);
        return ResponseEntity.ok(precioHistoricoDTO);
    }

    @PostMapping
    public ResponseEntity<PrecioHistoricoDTO> crearPrecioHistorico(@Valid @RequestBody PrecioHistoricoDTO precioHistoricoDTO){
        PrecioHistorico precioHistorico = precioHistoricoMapper.toEntity(precioHistoricoDTO);
        PrecioHistorico nuevoPrecioHistorico = precioHistoricoService.creaPrecioHistorico(precioHistorico);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(nuevoPrecioHistorico.getId()).toUri();
        return ResponseEntity.created(location).body(precioHistoricoMapper.toDTO(nuevoPrecioHistorico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrecioHistoricoDTO> actualizarPrecioHistorico(@Valid @PathVariable Long id, PrecioHistoricoDTO precioHistoricoDTO){
        PrecioHistorico precioHistorico = precioHistoricoMapper.toEntity(precioHistoricoDTO);
        PrecioHistorico precioHistoricoActualizado = precioHistoricoService.actualizarPrecioHistorico(precioHistorico, id);
        PrecioHistoricoDTO precioHistoricoActualizadoDTO = precioHistoricoMapper.toDTO(precioHistoricoActualizado);
        return ResponseEntity.ok(precioHistoricoActualizadoDTO);
    }

    @DeleteMapping
    public ResponseEntity<Void> eliminarPrecioHistorico(@Valid @PathVariable Long id){
        precioHistoricoService.eliminarPrecioHistoricoPorID(id);
        return ResponseEntity.noContent().build();
    }
}
