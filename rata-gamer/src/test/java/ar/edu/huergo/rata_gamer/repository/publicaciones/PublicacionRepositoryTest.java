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

import ar.edu.huergo.rata_gamer.entity.publicaciones.Juego;
import ar.edu.huergo.rata_gamer.entity.publicaciones.Plataforma;
import ar.edu.huergo.rata_gamer.entity.publicaciones.Publicacion;

@ActiveProfiles("test")
@DataJpaTest
@DisplayName("Tests de Integración - PublicacionRepository")
public class PublicacionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PublicacionRepository publicacionRepository;

    private Juego juego1;
    private Juego juego2;
    private Plataforma plataforma1;
    private Plataforma plataforma2;
    private Publicacion publicacion1;
    private Publicacion publicacion2;

    @BeforeEach
    void setUp() {
        // Crear juegos de prueba
        juego1 = new Juego();
        juego1.setNombre("The Legend of Zelda");
        juego1 = entityManager.persistAndFlush(juego1);

        juego2 = new Juego();
        juego2.setNombre("Super Mario Odyssey");
        juego2 = entityManager.persistAndFlush(juego2);

        // Crear plataformas de prueba
        plataforma1 = new Plataforma();
        plataforma1.setNombre("Nintendo Switch");
        plataforma1 = entityManager.persistAndFlush(plataforma1);

        plataforma2 = new Plataforma();
        plataforma2.setNombre("PlayStation 5");
        plataforma2 = entityManager.persistAndFlush(plataforma2);

        // Crear publicaciones de prueba
        publicacion1 = new Publicacion();
        publicacion1.setJuego(juego1);
        publicacion1.setPlataforma(plataforma1);
        publicacion1.setPreciosHistoricos(null);
        publicacion1 = entityManager.persistAndFlush(publicacion1);

        publicacion2 = new Publicacion();
        publicacion2.setJuego(juego2);
        publicacion2.setPlataforma(plataforma2);
        publicacion2.setPreciosHistoricos(null);
        publicacion2 = entityManager.persistAndFlush(publicacion2);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería guardar y recuperar publicación correctamente")
    void deberiaGuardarYRecuperarPublicacion() {
        // Given
        Juego nuevoJuego = new Juego();
        nuevoJuego.setNombre("FIFA 21");
        nuevoJuego = entityManager.persistAndFlush(nuevoJuego);

        Plataforma nuevaPlataforma = new Plataforma();
        nuevaPlataforma.setNombre("PC");
        nuevaPlataforma = entityManager.persistAndFlush(nuevaPlataforma);

        Publicacion nuevaPublicacion = new Publicacion();
        nuevaPublicacion.setJuego(nuevoJuego);
        nuevaPublicacion.setPlataforma(nuevaPlataforma);

        // When
        Publicacion publicacionGuardada = publicacionRepository.save(nuevaPublicacion);
        entityManager.flush();
        entityManager.clear();

        // Then
        assertNotNull(publicacionGuardada.getId());

        Optional<Publicacion> publicacionRecuperada =
                publicacionRepository.findById(publicacionGuardada.getId());

        assertTrue(publicacionRecuperada.isPresent());
        assertEquals("FIFA 21", publicacionRecuperada.get().getJuego().getNombre());
        assertEquals("PC", publicacionRecuperada.get().getPlataforma().getNombre());
    }

    @Test
    @DisplayName("Debería actualizar publicación existente")
    void deberiaActualizarPublicacionExistente() {
        // Given
        Juego nuevoJuego = new Juego();
        nuevoJuego.setNombre("Pokemon HeartGold");
        nuevoJuego = entityManager.persistAndFlush(nuevoJuego);

        // When
        Optional<Publicacion> publicacionOptional =
                publicacionRepository.findById(publicacion1.getId());
        assertTrue(publicacionOptional.isPresent());

        Publicacion publicacion = publicacionOptional.get();
        publicacion.setJuego(nuevoJuego);

        Publicacion publicacionActualizada = publicacionRepository.save(publicacion);
        entityManager.flush();

        // Then
        assertEquals("Pokemon HeartGold", publicacionActualizada.getJuego().getNombre());

        // Verificar persistencia
        entityManager.clear();
        Optional<Publicacion> publicacionVerificacion =
                publicacionRepository.findById(publicacion1.getId());
        assertTrue(publicacionVerificacion.isPresent());
        assertEquals("Pokemon HeartGold", publicacionVerificacion.get().getJuego().getNombre());
    }

    @Test
    @DisplayName("Debería eliminar publicación correctamente")
    void deberiaEliminarPublicacion() {
        // Given
        Long publicacionId = publicacion1.getId();
        assertTrue(publicacionRepository.existsById(publicacionId));

        // When
        publicacionRepository.deleteById(publicacionId);
        entityManager.flush();

        // Then
        assertFalse(publicacionRepository.existsById(publicacionId));
        Optional<Publicacion> publicacionEliminada = publicacionRepository.findById(publicacionId);
        assertFalse(publicacionEliminada.isPresent());
    }

    @Test
    @DisplayName("Debería encontrar todas las publicaciones")
    void deberiaEncontrarTodasLasPublicaciones() {
        // When
        List<Publicacion> todasLasPublicaciones = publicacionRepository.findAll();

        // Then
        assertNotNull(todasLasPublicaciones);
        assertEquals(2, todasLasPublicaciones.size());
    }

    @Test
    @DisplayName("Debería contar publicaciones correctamente")
    void deberiaContarPublicaciones() {
        // When
        long cantidadPublicaciones = publicacionRepository.count();

        // Then
        assertEquals(2, cantidadPublicaciones);

        // Agregar una publicación más y verificar
        Juego nuevoJuego = new Juego();
        nuevoJuego.setNombre("Minecraft");
        nuevoJuego = entityManager.persistAndFlush(nuevoJuego);

        Plataforma nuevaPlataforma = new Plataforma();
        nuevaPlataforma.setNombre("Xbox Series X");
        nuevaPlataforma = entityManager.persistAndFlush(nuevaPlataforma);

        Publicacion nuevaPublicacion = new Publicacion();
        nuevaPublicacion.setJuego(nuevoJuego);
        nuevaPublicacion.setPlataforma(nuevaPlataforma);
        entityManager.persistAndFlush(nuevaPublicacion);

        assertEquals(3, publicacionRepository.count());
    }

    @Test
    @DisplayName("Debería validar restricciones de la entidad")
    void deberiaValidarRestricciones() {
        // Given - Crear publicación con juego null
        Publicacion publicacionInvalida = new Publicacion();
        publicacionInvalida.setJuego(null);
        publicacionInvalida.setPlataforma(plataforma1);

        // When & Then
        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(publicacionInvalida);
        });

        // Given - Crear publicación con plataforma null
        Publicacion publicacionInvalida2 = new Publicacion();
        publicacionInvalida2.setJuego(juego1);
        publicacionInvalida2.setPlataforma(null);

        // When & Then
        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(publicacionInvalida2);
        });
    }
}
