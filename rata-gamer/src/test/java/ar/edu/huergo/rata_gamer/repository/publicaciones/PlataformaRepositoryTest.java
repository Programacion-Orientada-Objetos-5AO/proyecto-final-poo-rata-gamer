package ar.edu.huergo.rata_gamer.repository.publicaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import ar.edu.huergo.rata_gamer.entity.publicaciones.Plataforma;

@ActiveProfiles("test")
@DataJpaTest
@DisplayName("Tests de Integración - PlataformaRepository")
public class PlataformaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlataformaRepository plataformaRepository;

    private Plataforma plataforma1;
    private Plataforma plataforma2;
    private Plataforma plataforma3;

    @BeforeEach
    void setUp() {
        // Crear plataformas de prueba
        plataforma1 = new Plataforma();
        plataforma1.setNombre("PlayStation 5");
        plataforma1 = entityManager.persistAndFlush(plataforma1);

        plataforma2 = new Plataforma();
        plataforma2.setNombre("Xbox Series X");
        plataforma2 = entityManager.persistAndFlush(plataforma2);

        plataforma3 = new Plataforma();
        plataforma3.setNombre("Nintendo Switch");
        plataforma3 = entityManager.persistAndFlush(plataforma3);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar plataforma por nombre")
    void deberiaEncontrarPlataformaPorNombre() {
        // When
        Optional<Plataforma> plataformaEncontrada =
                plataformaRepository.findByNombre("PlayStation 5");

        // Then
        assertTrue(plataformaEncontrada.isPresent());
        assertEquals("PlayStation 5", plataformaEncontrada.get().getNombre());
    }

    @Test
    @DisplayName("Debería retornar vacío cuando no encuentra plataforma por nombre")
    void deberiaRetornarVacioCuandoNoEncuentraPlataformaPorNombre() {
        // When
        Optional<Plataforma> plataformaEncontrada =
                plataformaRepository.findByNombre("Inexistente");

        // Then
        assertFalse(plataformaEncontrada.isPresent());
    }

    @Test
    @DisplayName("Debería guardar y recuperar plataforma correctamente")
    void deberiaGuardarYRecuperarPlataforma() {
        // Given
        Plataforma nuevaPlataforma = new Plataforma();
        nuevaPlataforma.setNombre("PC");

        // When
        Plataforma plataformaGuardada = plataformaRepository.save(nuevaPlataforma);
        entityManager.flush();
        entityManager.clear();

        // Then
        assertNotNull(plataformaGuardada.getId());

        Optional<Plataforma> plataformaRecuperada =
                plataformaRepository.findById(plataformaGuardada.getId());

        assertTrue(plataformaRecuperada.isPresent());
        assertEquals("PC", plataformaRecuperada.get().getNombre());
    }

    @Test
    @DisplayName("Debería actualizar plataforma existente")
    void deberiaActualizarPlataformaExistente() {
        // Given
        String nuevoNombre = "PlayStation 5 Pro";

        // When
        Optional<Plataforma> plataformaOptional =
                plataformaRepository.findById(plataforma1.getId());
        assertTrue(plataformaOptional.isPresent());

        Plataforma plataforma = plataformaOptional.get();
        plataforma.setNombre(nuevoNombre);

        Plataforma plataformaActualizada = plataformaRepository.save(plataforma);
        entityManager.flush();

        // Then
        assertEquals(nuevoNombre, plataformaActualizada.getNombre());

        // Verificar persistencia
        entityManager.clear();
        Optional<Plataforma> plataformaVerificacion =
                plataformaRepository.findById(plataforma1.getId());
        assertTrue(plataformaVerificacion.isPresent());
        assertEquals(nuevoNombre, plataformaVerificacion.get().getNombre());
    }

    @Test
    @DisplayName("Debería eliminar plataforma correctamente")
    void deberiaEliminarPlataforma() {
        // Given
        Long plataformaId = plataforma1.getId();
        assertTrue(plataformaRepository.existsById(plataformaId));

        // When
        plataformaRepository.deleteById(plataformaId);
        entityManager.flush();

        // Then
        assertFalse(plataformaRepository.existsById(plataformaId));
        Optional<Plataforma> plataformaEliminada = plataformaRepository.findById(plataformaId);
        assertFalse(plataformaEliminada.isPresent());
    }

    @Test
    @DisplayName("Debería encontrar todas las plataformas")
    void deberiaEncontrarTodasLasPlataformas() {
        // When
        List<Plataforma> todasLasPlataformas = plataformaRepository.findAll();

        // Then
        assertNotNull(todasLasPlataformas);
        assertEquals(3, todasLasPlataformas.size());

        List<String> nombres = todasLasPlataformas.stream().map(Plataforma::getNombre).toList();
        assertTrue(nombres.contains("PlayStation 5"));
        assertTrue(nombres.contains("Xbox Series X"));
        assertTrue(nombres.contains("Nintendo Switch"));
    }

    @Test
    @DisplayName("Debería contar plataformas correctamente")
    void deberiaContarPlataformas() {
        // When
        long cantidadPlataformas = plataformaRepository.count();

        // Then
        assertEquals(3, cantidadPlataformas);

        // Agregar una plataforma más y verificar
        Plataforma nuevaPlataforma = new Plataforma();
        nuevaPlataforma.setNombre("Steam Deck");
        entityManager.persistAndFlush(nuevaPlataforma);

        assertEquals(4, plataformaRepository.count());
    }

    @Test
    @DisplayName("Debería validar restricciones de unicidad en nombre")
    void deberiaValidarRestriccionesUnicidad() {
        // Given - Crear plataforma con nombre duplicado
        Plataforma plataformaDuplicada = new Plataforma();
        plataformaDuplicada.setNombre("PlayStation 5"); // Ya existe

        // When & Then
        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(plataformaDuplicada);
        });
    }

    @Test
    @DisplayName("Debería validar restricciones de la entidad")
    void deberiaValidarRestricciones() {
        // Given - Crear plataforma con nombre vacío
        Plataforma plataformaInvalida = new Plataforma();
        plataformaInvalida.setNombre(""); // Viola @NotBlank

        // When & Then
        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(plataformaInvalida);
        });
    }
}
