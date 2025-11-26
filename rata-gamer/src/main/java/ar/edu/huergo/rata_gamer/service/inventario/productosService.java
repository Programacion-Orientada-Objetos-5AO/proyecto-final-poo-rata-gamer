package ar.edu.huergo.rata_gamer.service.inventario;

import ar.edu.huergo.rata_gamer.dto.inventario.ProductosRequestDTO;
import ar.edu.huergo.rata_gamer.dto.inventario.ProductosResponseDTO;
import ar.edu.huergo.rata_gamer.entity.inventario.Productos;
import ar.edu.huergo.rata_gamer.mapper.inventario.ProductosMapper;
import ar.edu.huergo.rata_gamer.repository.inventario.ProductosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class productosService {

    private final ProductosRepository repository;
    private final ProductosMapper mapper;

    public productosService(ProductosRepository repository, ProductosMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ProductosResponseDTO create(ProductosRequestDTO dto) {
        Productos producto = mapper.toEntity(dto);
        producto.setStock(0); 
        return ProductosMapper.toDTO(repository.save(producto));
    }

    public List<ProductosResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(ProductosMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProductosResponseDTO findById(Long id) {
        Productos producto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        return ProductosMapper.toDTO(producto);
    }

    public ProductosResponseDTO update(Long id, ProductosRequestDTO dto) {
        Productos producto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        producto.setNombre(dto.nombre());
        producto.setCategoria(dto.categoria());
        producto.setPrecio(dto.precio());

        return ProductosMapper.toDTO(repository.save(producto));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("No existe un producto con ID: " + id);
        }
        repository.deleteById(id);
    }
}
