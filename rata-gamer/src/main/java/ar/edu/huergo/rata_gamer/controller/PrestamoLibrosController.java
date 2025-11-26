package ar.edu.huergo.rata_gamer.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.edu.huergo.rata_gamer.dto.PrestamoLibrosDTO;
import ar.edu.huergo.rata_gamer.dto.PrestamoLibrosRequestDTO;
import ar.edu.huergo.rata_gamer.dto.publicaciones.PrecioHistoricoDTO;
import ar.edu.huergo.rata_gamer.entity.Libro;
import ar.edu.huergo.rata_gamer.entity.Publicaciones.PrecioHistorico;
import ar.edu.huergo.rata_gamer.mapper.PrestamoLibrosMapper;
import ar.edu.huergo.rata_gamer.mapper.PrestamosLibrosRequestMapper;
import ar.edu.huergo.rata_gamer.service.PrestamoLibrosService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/prestamos-libros")
public class PrestamoLibrosController {
 
    @Autowired
    private PrestamoLibrosMapper prestamoLibrosMapper;

    @Autowired
    private PrestamosLibrosRequestMapper prestamosLibrosRequestMapper;

    @Autowired
    private PrestamoLibrosService prestamoLibrosService;

    @GetMapping
    public ResponseEntity<List<PrestamoLibrosDTO>> obtenerPrestamos(){
        List<Libro> libros = prestamoLibrosService.obtenerTodosLosPrestamos();
        List<PrestamoLibrosDTO> prestamoLibrosDTO = prestamoLibrosMapper.toDtoList(libros);
        return ResponseEntity.ok(prestamoLibrosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestamoLibrosDTO> obtenerPrestamoPorId(@PathVariable Long id){
        Libro prestamoLibro = prestamoLibrosService.obtenerPrestamoPorId(id);
        PrestamoLibrosDTO prestamoLibroDTO = prestamoLibrosMapper.toDTO(prestamoLibro);
        return ResponseEntity.ok(prestamoLibroDTO);
    }
    

    @PostMapping
    public ResponseEntity<PrestamoLibrosDTO> crearPrestamoLibro(@Valid @RequestBody PrestamoLibrosRequestDTO prestamoLibrosRequestDTO){
        Libro libro = prestamosLibrosRequestMapper.toEntity(prestamoLibrosRequestDTO);
        Libro nuevoLibro = prestamoLibrosService.crearPrestamoLibro(libro);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(nuevoLibro.getId()).toUri();
        return ResponseEntity.created(location).body(prestamoLibrosMapper.toDTO(nuevoLibro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrestamoLibrosDTO> actualizarPrestamoLibro(@PathVariable Long id,@Valid @RequestBody PrestamoLibrosRequestDTO prestamoLibrosRequestDTO){
        Libro libro = prestamosLibrosRequestMapper.toEntity(prestamoLibrosRequestDTO);
        Libro nuevoLibro = prestamoLibrosService.actualizarPrestamoLibro(id, libro);
        PrestamoLibrosDTO nuevoLibroActualizado = prestamoLibrosMapper.toDTO(nuevoLibro);
        return ResponseEntity.ok(nuevoLibroActualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PrestamoLibrosDTO> actualizarParcialmenteLibro(@PathVariable Long id,@Valid @RequestBody PrestamoLibrosDTO prestamoLibrosDTO){
        Libro libro = prestamoLibrosMapper.toEntity(prestamoLibrosDTO);
        Libro nuevoLibro = prestamoLibrosService.actualizarParcialPrestamoLibro(id, libro);
        PrestamoLibrosDTO nuevoLibroActualizado = prestamoLibrosMapper.toDTO(nuevoLibro);
        return ResponseEntity.ok(nuevoLibroActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrestamoLibro(@PathVariable Long id){
        prestamoLibrosService.eliminarPrestamoLibro(id);
        return ResponseEntity.noContent().build();
    }
    
}
