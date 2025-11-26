package ar.edu.huergo.rata_gamer.mapper.inventario;

import org.springframework.stereotype.Component;
import ar.edu.huergo.rata_gamer.dto.inventario.ProductosRequestDTO;
import ar.edu.huergo.rata_gamer.dto.inventario.ProductosResponseDTO;
import ar.edu.huergo.rata_gamer.entity.inventario.Productos;


@Component
public class ProductosMapper {

  // RequestDTO --> Entidad Producto
  public Productos toEntity(ProductosRequestDTO dto) {
  Productos productos = new Productos();
  productos.setNombre(dto.nombre());
  productos.setCategoria(dto.categoria());
  productos.setPrecio(dto.precio());
  return productos;
  }

  // Entidad Producto --> ResponseDTO
  public static ProductosResponseDTO toDTO(Productos p) {
    return new ProductosResponseDTO(
      p.getId(),
      p.getNombre(),
      p.getCategoria(),
      p.getPrecio(),
      p.getStock()
        );
    }
  }
