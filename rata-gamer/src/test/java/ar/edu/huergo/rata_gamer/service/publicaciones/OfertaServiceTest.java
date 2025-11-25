package ar.edu.huergo.rata_gamer.service.publicaciones;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.huergo.rata_gamer.entity.Publicaciones.Oferta;
import ar.edu.huergo.rata_gamer.repository.publicaciones.OfertaRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - OfertaService")
class OfertaServiceTest {

    @Mock
    private OfertaRepository ofertaRepository;

    @InjectMocks
    private OfertaService ofertaService;

    private Oferta ofertaEjemplo;

    @BeforeEach
    void setUp() {
        ofertaEjemplo = new Oferta();
        ofertaEjemplo.setId(1L);
        ofertaEjemplo.setDescripcion("Descuento del 20%");
        ofertaEjemplo.setDescuento(20.0);
        ofertaEjemplo.setFechaInicio(LocalDate.now());
        ofertaEjemplo.setFechaFin(LocalDate.now().plusDays(30));
    }

    @Test
    @DisplayName("Debería obtener todas las ofertas")
    void deberiaObtenerTodasLasOfertas() {
        // Given
        List<Oferta> ofertasEsperadas = Arrays.asList(ofertaEjemplo);
        when(ofertaRepository.findAll()).thenReturn(ofertasEsperadas);

        // When
        List<Oferta> resultado = ofertaService.obtenerTodasLasOfertas();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(ofertaEjemplo.getDescripcion(), resultado.get(0).getDescripcion());
        verify(ofertaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener oferta por ID cuando existe")
    void deberiaObtenerOfertaPorId() {
        // Given
        Long ofertaId = 1L;
        when(ofertaRepository.findById(ofertaId)).thenReturn(Optional.of(ofertaEjemplo));

        // When
        Oferta resultado = ofertaService.obtenerOfertaPorId(ofertaId);

        // Then
        assertNotNull(resultado);
        assertEquals(ofertaEjemplo.getId(), resultado.getId());
        assertEquals(ofertaEjemplo.getDescripcion(), resultado.getDescripcion());
        verify(ofertaRepository, times(1)).findById(ofertaId);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando oferta no existe")
    void deberiaLanzarExcepcionCuandoOfertaNoExiste() {
        // Given
        Long ofertaIdInexistente = 999L;
        when(ofertaRepository.findById(ofertaIdInexistente)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException excepcion = assertThrows(EntityNotFoundException.class,
                () -> ofertaService.obtenerOfertaPorId(ofertaIdInexistente));

        assertEquals("Oferta no encontrada", excepcion.getMessage());
        verify(ofertaRepository, times(1)).findById(ofertaIdInexistente);
    }

    @Test
    @DisplayName("Debería crear oferta correctamente")
    void deberiaCrearOferta() {
        // Given
        Oferta nuevaOferta = new Oferta();
        nuevaOferta.setDescripcion("Nueva oferta especial");
        nuevaOferta.setDescuento(25.0);
        nuevaOferta.setFechaInicio(LocalDate.now());
        nuevaOferta.setFechaFin(LocalDate.now().plusDays(15));

        when(ofertaRepository.save(nuevaOferta)).thenReturn(nuevaOferta);

        // When
        Oferta resultado = ofertaService.crearOferta(nuevaOferta);

        // Then
        assertNotNull(resultado);
        assertEquals(nuevaOferta.getDescripcion(), resultado.getDescripcion());
        verify(ofertaRepository, times(1)).save(nuevaOferta);
    }

    @Test
    @DisplayName("Debería actualizar oferta existente")
    void deberiaActualizarOferta() {
        // Given
        Long ofertaId = 1L;
        Oferta ofertaActualizada = new Oferta();
        ofertaActualizada.setDescripcion("Descuento actualizado al 30%");

        when(ofertaRepository.findById(ofertaId)).thenReturn(Optional.of(ofertaEjemplo));
        when(ofertaRepository.save(any(Oferta.class))).thenReturn(ofertaEjemplo);

        // When
        Oferta resultado = ofertaService.actualizarOferta(ofertaActualizada, ofertaId);

        // Then
        assertNotNull(resultado);
        verify(ofertaRepository, times(1)).findById(ofertaId);
        verify(ofertaRepository, times(1)).save(ofertaEjemplo);
        assertEquals(ofertaActualizada.getDescripcion(), ofertaEjemplo.getDescripcion());
    }

    @Test
    @DisplayName("Debería eliminar oferta existente")
    void deberiaEliminarOferta() {
        // Given
        Long ofertaId = 1L;
        when(ofertaRepository.findById(ofertaId)).thenReturn(Optional.of(ofertaEjemplo));
        doNothing().when(ofertaRepository).delete(ofertaEjemplo);

        // When
        assertDoesNotThrow(() -> ofertaService.eliminarOferta(ofertaId));

        // Then
        verify(ofertaRepository, times(1)).findById(ofertaId);
        verify(ofertaRepository, times(1)).delete(ofertaEjemplo);
    }
}
