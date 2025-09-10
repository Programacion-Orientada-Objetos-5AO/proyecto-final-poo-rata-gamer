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
import ar.edu.huergo.rata_gamer.entity.publicaciones.Juego;

@DataJpaTest
@DisplayName("Tests de Integración - JuegoRepository")
public class JuegoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JuegoRepository juegoRepository;

    private Juego juego1;
    private Juego juego2;
    private Juego juego3;

    @BeforeEach
    void setUp() {
        // Crear juegos de prueba
        juego1 = new Juego();
        juego1.setNombre("Plantas vs Zombies");
        juego1 = entityManager.persistAndFlush(juego1);

        juego2 = new Juego();
        juego2.setNombre("Plantas vs Zombies Garden Warfare");
        juego2 = entityManager.persistAndFlush(juego2);

        juego3 = new Juego();
        juego3.setNombre("Fortnite");
        juego3 = entityManager.persistAndFlush(juego3);

        entityManager.clear();

    }



    @Test
    @DisplayName("Debería encontrar juegos por nombre conteniendo texto (case insensitive)")
    void deberiaEncontrarJuegosPorNombreContaining() {
        // When - Buscar juegos que contengan "fortnite"
        List<Juego> juegosEncontrados =
                juegoRepository.findByNombreContainingIgnoreCase("Plantas vs Zombies");

        // Then
        assertNotNull(juegosEncontrados);
        assertEquals(2, juegosEncontrados.size());

        List<String> nombres =
                juegosEncontrados.stream().map(Juego::getNombre).toList();
        assertTrue(nombres.contains("Plantas vs Zombies"));
        assertTrue(nombres.contains("Plantas vs Zombies Garden Warfare"));
    }

    @Test
    @DisplayName("Debería encontrar juegos con búsqueda case insensitive")
    void deberiaEncontrarJuegosCaseInsensitive() {
        // When - Buscar con diferentes cases
        List<Juego> resultadoMinuscula =
                juegoRepository.findByNombreContainingIgnoreCase("PLANTAS VS ZOMBIES");
        List<Juego> resultadoMayuscula =
                juegoRepository.findByNombreContainingIgnoreCase("plantas vs zombies");
        List<Juego> resultadoMixto =
                juegoRepository.findByNombreContainingIgnoreCase("PLanTaS vS ZOMbIeS");

        // Then - Todos deberían dar el mismo resultado
        assertEquals(2, resultadoMinuscula.size());
        assertEquals(2, resultadoMayuscula.size());
        assertEquals(2, resultadoMixto.size());
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no encuentra coincidencias")
    void deberiaRetornarListaVaciaSinCoincidencias() {
        // When
        List<Juego> juegosEncontrados =
                juegoRepository.findByNombreContainingIgnoreCase("inexistente");

        // Then
        assertNotNull(juegosEncontrados);
        assertTrue(juegosEncontrados.isEmpty());
    }

    @Test
    @DisplayName("Debería encontrar juegos con búsqueda parcial")
    void deberiaEncontrarJuegosConBusquedaParcial() {
        // When - Buscar solo parte del nombre
        List<Juego> resultadoPlan =
                juegoRepository.findByNombreContainingIgnoreCase("plan");
        List<Juego> resultadoFort =
                juegoRepository.findByNombreContainingIgnoreCase("fort");

        // Then
        assertEquals(1, resultadoPlan.size());
        assertEquals("Plantas vs Zombies ", resultadoPlan.get(0).getNombre());

        assertEquals(1, resultadoFort.size());
        assertEquals("Fortnite", resultadoFort.get(0).getNombre());
    }

    @Test
    @DisplayName("Debería guardar y recuperar juego correctamente")
    void deberiaGuardarYRecuperarJuego() {
        // Given
        Juego nuevoJuego = new Juego();
        nuevoJuego.setNombre("Club Penguin");

        // When
        Juego juegoGuardado = juegoRepository.save(nuevoJuego);
        entityManager.flush();
        entityManager.clear();

        // Then
        assertNotNull(juegoGuardado.getId());

        Optional<Juego> juegoRecuperado =
                juegoRepository.findById(juegoGuardado.getId());

        assertTrue(juegoRecuperado.isPresent());
        assertEquals("Club Penguin", juegoRecuperado.get().getNombre());
    }

    @Test
    @DisplayName("Debería actualizar juego existente")
    void deberiaActualizarJuegoExistente() {
        // Given
        String nuevoNombre = "Plantas vs Zombies: Garden Warfare";

        // When
        Optional<Juego> juegoOptional =
                juegoRepository.findById(juego1.getId());
        assertTrue(juegoOptional.isPresent());

        Juego juego = juegoOptional.get();
        juego.setNombre(nuevoNombre);

        Juego juegoActualizado = juegoRepository.save(juego);
        entityManager.flush();

        // Then
        assertEquals(nuevoNombre, juegoActualizado.getNombre());

        // Verificar persistencia
        entityManager.clear();
        Optional<Juego> juegoVerificacion =
                juegoRepository.findById(juego1.getId());
        assertTrue(juegoVerificacion.isPresent());
        assertEquals(nuevoNombre, juegoVerificacion.get().getNombre());
    }
}
