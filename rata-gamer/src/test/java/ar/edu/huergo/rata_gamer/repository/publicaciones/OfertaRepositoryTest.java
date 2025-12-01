package ar.edu.huergo.rata_gamer.repository.publicaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import ar.edu.huergo.rata_gamer.entity.Publicaciones.Oferta;

@ActiveProfiles("test")
@DataJpaTest
@DisplayName("Tests de Integración - OfertaRepository")
public class OfertaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OfertaRepository ofertaRepository;

    private Oferta oferta1;
    private Oferta oferta2;
    private Oferta oferta3;

    @BeforeEach
    void setUp() {
        // Crear ofertas de prueba
        oferta1 = new Oferta();
        oferta1.setDescripcion("Descuento del 20% en juegos de aventura");
        oferta1.setDescuento(20.0);
        oferta1.setFechaInicio(LocalDate.now());
        oferta1.setFechaFin(LocalDate.now().plusDays(30));
        oferta1 = entityManager.persistAndFlush(oferta1);

        oferta2 = new Oferta();
        oferta2.setDescripcion("Descuento del 15% en juegos de acción");
        oferta2.setDescuento(15.0);
        oferta2.setFechaInicio(LocalDate.now().plusDays(5));
        oferta2.setFechaFin(LocalDate.now().plusDays(20));
        oferta2 = entityManager.persistAndFlush(oferta2);

        oferta3 = new Oferta();
        oferta3.setDescripcion("Descuento del 10% en juegos indie");
        oferta3.setDescuento(10.0);
        oferta3.setFechaInicio(LocalDate.now().minusDays(10));
        oferta3.setFechaFin(LocalDate.now().plusDays(10));
        oferta3 = entityManager.persistAndFlush(oferta3);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería guardar y recuperar oferta correctamente")
    void deberiaGuardarYRecuperarOferta() {
        // Given
        Oferta nuevaOferta = new Oferta();
        nuevaOferta.setDescripcion("Nueva oferta especial");
        nuevaOferta.setDescuento(25.0);
        nuevaOferta.setFechaInicio(LocalDate.now());
        nuevaOferta.setFechaFin(LocalDate.now().plusDays(15));

        // When
        Oferta ofertaGuardada = ofertaRepository.save(nuevaOferta);
        entityManager.flush();
        entityManager.clear();

        // Then
        assertNotNull(ofertaGuardada.getId());

        Optional<Oferta> ofertaRecuperada = ofertaRepository.findById(ofertaGuardada.getId());

        assertTrue(ofertaRecuperada.isPresent());
        assertEquals("Nueva oferta especial", ofertaRecuperada.get().getDescripcion());
        assertEquals(25.0, ofertaRecuperada.get().getDescuento());
    }

    @Test
    @DisplayName("Debería actualizar oferta existente")
    void deberiaActualizarOfertaExistente() {
        // Given
        String nuevaDescripcion = "Descuento actualizado al 25%";

        // When
        Optional<Oferta> ofertaOptional = ofertaRepository.findById(oferta1.getId());
        assertTrue(ofertaOptional.isPresent());

        Oferta oferta = ofertaOptional.get();
        oferta.setDescripcion(nuevaDescripcion);

        Oferta ofertaActualizada = ofertaRepository.save(oferta);
        entityManager.flush();

        // Then
        assertEquals(nuevaDescripcion, ofertaActualizada.getDescripcion());

        // Verificar persistencia
        entityManager.clear();
        Optional<Oferta> ofertaVerificacion = ofertaRepository.findById(oferta1.getId());
        assertTrue(ofertaVerificacion.isPresent());
        assertEquals(nuevaDescripcion, ofertaVerificacion.get().getDescripcion());
    }

    @Test
    @DisplayName("Debería eliminar oferta correctamente")
    void deberiaEliminarOferta() {
        // Given
        Long ofertaId = oferta1.getId();
        assertTrue(ofertaRepository.existsById(ofertaId));

        // When
        ofertaRepository.deleteById(ofertaId);
        entityManager.flush();

        // Then
        assertFalse(ofertaRepository.existsById(ofertaId));
        Optional<Oferta> ofertaEliminada = ofertaRepository.findById(ofertaId);
        assertFalse(ofertaEliminada.isPresent());
    }

    @Test
    @DisplayName("Debería encontrar todas las ofertas")
    void deberiaEncontrarTodasLasOfertas() {
        // When
        List<Oferta> todasLasOfertas = ofertaRepository.findAll();

        // Then
        assertNotNull(todasLasOfertas);
        assertEquals(3, todasLasOfertas.size());

        List<String> descripciones = todasLasOfertas.stream().map(Oferta::getDescripcion).toList();
        assertTrue(descripciones.contains("Descuento del 20% en juegos de aventura"));
        assertTrue(descripciones.contains("Descuento del 15% en juegos de acción"));
        assertTrue(descripciones.contains("Descuento del 10% en juegos indie"));
    }

    @Test
    @DisplayName("Debería contar ofertas correctamente")
    void deberiaContarOfertas() {
        // When
        long cantidadOfertas = ofertaRepository.count();

        // Then
        assertEquals(3, cantidadOfertas);

        // Agregar una oferta más y verificar
        Oferta nuevaOferta = new Oferta();
        nuevaOferta.setDescripcion("Descuento adicional");
        nuevaOferta.setDescuento(5.0);
        nuevaOferta.setFechaInicio(LocalDate.now());
        nuevaOferta.setFechaFin(LocalDate.now().plusDays(5));
        entityManager.persistAndFlush(nuevaOferta);

        assertEquals(4, ofertaRepository.count());
    }
}
