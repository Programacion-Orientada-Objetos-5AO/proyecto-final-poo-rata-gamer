package ar.edu.huergo.rata_gamer.repository.inventario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ar.edu.huergo.rata_gamer.entity.inventario.Productos;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Long> {
}