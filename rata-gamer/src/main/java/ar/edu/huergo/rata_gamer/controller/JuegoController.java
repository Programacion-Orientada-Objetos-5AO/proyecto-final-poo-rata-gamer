package ar.edu.huergo.rata_gamer.controller;

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

import ar.edu.huergo.rata_gamer.dto.JuegoDTO;
import ar.edu.huergo.rata_gamer.entity.Juego;
import ar.edu.huergo.rata_gamer.mapper.JuegoMapper;
import ar.edu.huergo.rata_gamer.service.JuegoService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/juegos")
public class JuegoController {
    @Autowired
    private JuegoService juegoService;

    @Autowired
    private JuegoMapper juegoMapper;

    @GetMapping
    public ResponseEntity<List<JuegoDTO>> obtenerJuegos() {
        List<Juego> juegos = juegoService.obtenerTodosLosJuegos();
        List<JuegoDTO> juegosDTO = juegoMapper.toDTOList(juegos);
        return ResponseEntity.ok(juegosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JuegoDTO> obtenerJuegoPorId(@PathVariable Long id) {
        Juego juego = juegoService.obtenerJuegoPorID(id);
        JuegoDTO juegoDTO = juegoMapper.toDTO(juego);
        return ResponseEntity.ok(juegoDTO);
    }

    @PostMapping
    public ResponseEntity<JuegoDTO> crearJuego(@Valid @RequestBody JuegoDTO juegoDTO) {
        Juego juego = juegoMapper.toEntity(juegoDTO);
        Juego nuevoJuego = juegoService.crearJuego(juego);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(nuevoJuego.getId()).toUri();
        return ResponseEntity.created(location).body(juegoMapper.toDTO(nuevoJuego));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JuegoDTO> actualizarJuego(@PathVariable Long id, JuegoDTO juegoDTO) {
        Juego juego = juegoMapper.toEntity(juegoDTO);
        Juego juegoActualizado = juegoService.actualizarJuego(juego, id);
        JuegoDTO juegoActualizadoDTO = juegoMapper.toDTO(juegoActualizado);
        return ResponseEntity.ok(juegoActualizadoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJuego(@PathVariable Long id) {
        juegoService.eliminarJuego(id);
        return ResponseEntity.noContent().build();
    }
}
