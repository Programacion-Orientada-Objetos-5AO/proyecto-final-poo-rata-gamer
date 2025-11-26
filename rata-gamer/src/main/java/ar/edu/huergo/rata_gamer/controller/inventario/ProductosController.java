package ar.edu.huergo.rata_gamer.controller.inventario;

import ar.edu.huergo.rata_gamer.dto.inventario.ProductosRequestDTO;
import ar.edu.huergo.rata_gamer.dto.inventario.ProductosResponseDTO;
import ar.edu.huergo.rata_gamer.service.inventario.productosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventario/productos")
public class ProductosController {

    private final productosService service;

    public ProductosController(productosService service) {
        this.service = service;
    }

    // crear
    @PostMapping
    public ResponseEntity<ProductosResponseDTO> create(@RequestBody ProductosRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    // leer
    @GetMapping
    public ResponseEntity<List<ProductosResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // lee por id
    @GetMapping("/{id}")
    public ResponseEntity<ProductosResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // actualizar
    @PutMapping("/{id}")
    public ResponseEntity<ProductosResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ProductosRequestDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // borrar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

