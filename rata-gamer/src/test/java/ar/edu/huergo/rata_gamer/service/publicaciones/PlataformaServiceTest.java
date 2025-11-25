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

import ar.edu.huergo.rata_gamer.entity.Publicaciones.Plataforma;
import ar.edu.huergo.rata_gamer.repository.publicaciones.PlataformaRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - PlataformaService")
class PlataformaServiceTest {

    @Mock
    private PlataformaRepository plataformaRepository;

    @InjectMocks
    private PlataformaService plataformaService;

    private Plataforma plataformaEjemplo;

    @BeforeEach
    void setUp() {
        plataformaEjemplo = new Plataforma();
        plataformaEjemplo.setId(1L);
        plataformaEjemplo.setNombre("PlayStation 5");
    }

    @Test
    @DisplayName("Debería obtener todas las plataformas")
    void deberiaObtenerTodasLasPlataformas() {
        // Given
        List<Plataforma> plataformasEsperadas = Arrays.asList(plataformaEjemplo);
        when(plataformaRepository.findAll()).thenReturn(plataformasEsperadas);

        // When
        List<Plataforma> resultado = plataformaService.obtenerTodasLasPlataformas();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(plataformaEjemplo.getNombre(), resultado.get(0).getNombre());
        verify(plataformaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener plataforma por ID cuando existe")
    void deberiaObtenerPlataformaPorId() {
        // Given
        Long plataformaId = 1L;
        when(plataformaRepository.findById(plataformaId))
                .thenReturn(Optional.of(plataformaEjemplo));

        // When
        Plataforma resultado = plataformaService.obtenerPlataformaPorID(plataformaId);

        // Then
        assertNotNull(resultado);
        assertEquals(plataformaEjemplo.getId(), resultado.getId());
        assertEquals(plataformaEjemplo.getNombre(), resultado.getNombre());
        verify(plataformaRepository, times(1)).findById(plataformaId);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando plataforma no existe")
    void deberiaLanzarExcepcionCuandoPlataformaNoExiste() {
        // Given
        Long plataformaIdInexistente = 999L;
        when(plataformaRepository.findById(plataformaIdInexistente)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException excepcion = assertThrows(EntityNotFoundException.class,
                () -> plataformaService.obtenerPlataformaPorID(plataformaIdInexistente));

        assertEquals("Plataforma no encontrada", excepcion.getMessage());
        verify(plataformaRepository, times(1)).findById(plataformaIdInexistente);
    }

    @Test
    @DisplayName("Debería obtener plataforma por nombre")
    void deberiaObtenerPlataformaPorNombre() {
        // Given
        String nombre = "PlayStation 5";
        when(plataformaRepository.findByNombre(nombre))
                .thenReturn(Optional.of(plataformaEjemplo));

        // When
        Plataforma resultado = plataformaService.obtenerPlataformaPorNombre(nombre);

        // Then
        assertNotNull(resultado);
        assertEquals(plataformaEjemplo.getNombre(), resultado.getNombre());
        verify(plataformaRepository, times(1)).findByNombre(nombre);
    }

    @Test
    @DisplayName("Debería crear plataforma correctamente")
    void deberiaCrearPlataforma() {
        // Given
        Plataforma nuevaPlataforma = new Plataforma();
        nuevaPlataforma.setNombre("Xbox Series X");

        when(plataformaRepository.save(nuevaPlataforma)).thenReturn(nuevaPlataforma);

        // When
        Plataforma resultado = plataformaService.crearPlataforma(nuevaPlataforma);

        // Then
        assertNotNull(resultado);
        assertEquals(nuevaPlataforma.getNombre(), resultado.getNombre());
        verify(plataformaRepository, times(1)).save(nuevaPlataforma);
    }

    @Test
    @DisplayName("Debería actualizar plataforma existente")
    void deberiaActualizarPlataforma() {
        // Given
        Long plataformaId = 1L;
        Plataforma plataformaActualizada = new Plataforma();
        plataformaActualizada.setNombre("Nintendo Switch");

        when(plataformaRepository.findById(plataformaId))
                .thenReturn(Optional.of(plataformaEjemplo));
        when(plataformaRepository.save(any(Plataforma.class))).thenReturn(plataformaEjemplo);

        // When
        Plataforma resultado =
                plataformaService.actualizarPlataforma(plataformaActualizada, plataformaId);

        // Then
        assertNotNull(resultado);
        verify(plataformaRepository, times(1)).findById(plataformaId);
        verify(plataformaRepository, times(1)).save(plataformaEjemplo);
        assertEquals(plataformaActualizada.getNombre(), plataformaEjemplo.getNombre());
    }

    @Test
    @DisplayName("Debería eliminar plataforma existente")
    void deberiaEliminarPlataforma() {
        // Given
        Long plataformaId = 1L;
        when(plataformaRepository.findById(plataformaId))
                .thenReturn(Optional.of(plataformaEjemplo));
        doNothing().when(plataformaRepository).delete(plataformaEjemplo);

        // When
        assertDoesNotThrow(() -> plataformaService.eliminarPlataforma(plataformaId));

        // Then
        verify(plataformaRepository, times(1)).findById(plataformaId);
        verify(plataformaRepository, times(1)).delete(plataformaEjemplo);
    }
}
