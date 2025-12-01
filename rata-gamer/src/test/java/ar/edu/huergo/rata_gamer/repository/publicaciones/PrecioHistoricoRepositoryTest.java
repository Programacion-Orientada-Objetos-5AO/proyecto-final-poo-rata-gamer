package ar.edu.huergo.rata_gamer.repository.publicaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ar.edu.huergo.rata_gamer.entity.Publicaciones.PrecioHistorico;

@DataJpaTest
@DisplayName("Tests de Integración - PrecioHistoricoRepository")
public class PrecioHistoricoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PrecioHistoricoRepository precioHistoricoRepository;

    private PrecioHistorico precio1;
    private PrecioHistorico precio2;
    private PrecioHistorico precio3;

    @BeforeEach
    void setUp() {
        // Crear precios históricos de prueba
        precio1 = new PrecioHistorico();
        precio1.setFechaInicio(LocalDate.of(2023, 1, 1));
        precio1.setFechaFin(LocalDate.of(2023, 6, 30));
        precio1.setPrecio(BigDecimal.valueOf(49.99));
        precio1 = entityManager.persistAndFlush(precio1);

        precio2 = new PrecioHistorico();
        precio2.setFechaInicio(LocalDate.of(2023, 7, 1));
        precio2.setFechaFin(LocalDate.of(2023, 12, 31));
        precio2.setPrecio(BigDecimal.valueOf(59.99));
        precio2 = entityManager.persistAndFlush(precio2);

        precio3 = new PrecioHistorico();
        precio3.setFechaInicio(LocalDate.of(2024, 1, 1));
        precio3.setPrecio(BigDecimal.valueOf(69.99));
        precio3 = entityManager.persistAndFlush(precio3);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería guardar y recuperar precio histórico correctamente")
    void deberiaGuardarYRecuperarPrecioHistorico() {
        // Given
        PrecioHistorico nuevoPrecio = new PrecioHistorico();
        nuevoPrecio.setFechaInicio(LocalDate.of(2024, 6, 1));
        nuevoPrecio.setPrecio(BigDecimal.valueOf(79.99));

        // When
        PrecioHistorico precioGuardado = precioHistoricoRepository.save(nuevoPrecio);
        entityManager.flush();
        entityManager.clear();

        // Then
        assertNotNull(precioGuardado.getId());

        Optional<PrecioHistorico> precioRecuperado =
                precioHistoricoRepository.findById(precioGuardado.getId());

        assertTrue(precioRecuperado.isPresent());
        assertEquals(BigDecimal.valueOf(79.99), precioRecuperado.get().getPrecio());
        assertEquals(LocalDate.of(2024, 6, 1), precioRecuperado.get().getFechaInicio());
    }

    @Test
    @DisplayName("Debería actualizar precio histórico existente")
    void deberiaActualizarPrecioHistoricoExistente() {
        // Given
        BigDecimal nuevoPrecio = BigDecimal.valueOf(89.99);

        // When
        Optional<PrecioHistorico> precioOptional =
                precioHistoricoRepository.findById(precio1.getId());
        assertTrue(precioOptional.isPresent());

        PrecioHistorico precio = precioOptional.get();
        precio.setPrecio(nuevoPrecio);

        PrecioHistorico precioActualizado = precioHistoricoRepository.save(precio);
        entityManager.flush();

        // Then
        assertEquals(nuevoPrecio, precioActualizado.getPrecio());

        // Verificar persistencia
        entityManager.clear();
        Optional<PrecioHistorico> precioVerificacion =
                precioHistoricoRepository.findById(precio1.getId());
        assertTrue(precioVerificacion.isPresent());
        assertEquals(nuevoPrecio, precioVerificacion.get().getPrecio());
    }

    @Test
    @DisplayName("Debería eliminar precio histórico correctamente")
    void deberiaEliminarPrecioHistorico() {
        // Given
        Long precioId = precio1.getId();
        assertTrue(precioHistoricoRepository.existsById(precioId));

        // When
        precioHistoricoRepository.deleteById(precioId);
        entityManager.flush();

        // Then
        assertFalse(precioHistoricoRepository.existsById(precioId));
        Optional<PrecioHistorico> precioEliminado = precioHistoricoRepository.findById(precioId);
        assertFalse(precioEliminado.isPresent());
    }

    @Test
    @DisplayName("Debería encontrar todos los precios históricos")
    void deberiaEncontrarTodosLosPreciosHistoricos() {
        // When
        List<PrecioHistorico> todosLosPrecios = precioHistoricoRepository.findAll();

        // Then
        assertNotNull(todosLosPrecios);
        assertEquals(3, todosLosPrecios.size());

        List<BigDecimal> precios = todosLosPrecios.stream().map(PrecioHistorico::getPrecio).toList();
        assertTrue(precios.contains(BigDecimal.valueOf(49.99)));
        assertTrue(precios.contains(BigDecimal.valueOf(59.99)));
        assertTrue(precios.contains(BigDecimal.valueOf(69.99)));
    }

    @Test
    @DisplayName("Debería contar precios históricos correctamente")
    void deberiaContarPreciosHistoricos() {
        // When
        long cantidadPrecios = precioHistoricoRepository.count();

        // Then
        assertEquals(3, cantidadPrecios);

        // Agregar un precio más y verificar
        PrecioHistorico nuevoPrecio = new PrecioHistorico();
        nuevoPrecio.setFechaInicio(LocalDate.of(2024, 7, 1));
        nuevoPrecio.setPrecio(BigDecimal.valueOf(99.99));
        entityManager.persistAndFlush(nuevoPrecio);

        assertEquals(4, precioHistoricoRepository.count());
    }

    @Test
    @DisplayName("Debería validar restricciones de la entidad")
    void deberiaValidarRestricciones() {
        // Given - Crear precio histórico con fechaInicio null
        PrecioHistorico precioInvalido = new PrecioHistorico();
        precioInvalido.setFechaInicio(null); // Viola @NotNull
        precioInvalido.setPrecio(BigDecimal.valueOf(29.99));

        // When & Then
        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(precioInvalido);
        });
    }

    @Test
    @DisplayName("Debería manejar precios con diferentes escalas")
    void deberiaManejarPreciosConDiferentesEscalas() {
        // Given
        PrecioHistorico precioEntero = new PrecioHistorico();
        precioEntero.setFechaInicio(LocalDate.of(2024, 8, 1));
        precioEntero.setPrecio(BigDecimal.valueOf(100)); // Sin decimales

        PrecioHistorico precioDecimal = new PrecioHistorico();
        precioDecimal.setFechaInicio(LocalDate.of(2024, 9, 1));
        precioDecimal.setPrecio(BigDecimal.valueOf(49.50)); // Un decimal

        // When
        PrecioHistorico precioEnteroGuardado = entityManager.persistAndFlush(precioEntero);
        PrecioHistorico precioDecimalGuardado = entityManager.persistAndFlush(precioDecimal);

        // Then
        assertEquals(BigDecimal.valueOf(100), precioEnteroGuardado.getPrecio());
        assertEquals(BigDecimal.valueOf(49.50), precioDecimalGuardado.getPrecio());
    }

    @Test
    @DisplayName("Debería manejar fechas de fin opcionales")
    void deberiaManejarFechasDeFinOpcionales() {
        // Given
        PrecioHistorico precioSinFechaFin = new PrecioHistorico();
        precioSinFechaFin.setFechaInicio(LocalDate.of(2024, 10, 1));
        precioSinFechaFin.setPrecio(BigDecimal.valueOf(39.99));

        // When
        PrecioHistorico precioGuardado = entityManager.persistAndFlush(precioSinFechaFin);

        // Then
        assertNotNull(precioGuardado);
        assertNull(precioGuardado.getFechaFin());
    }
}
