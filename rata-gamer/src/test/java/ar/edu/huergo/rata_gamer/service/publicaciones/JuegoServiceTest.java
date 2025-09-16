package ar.edu.huergo.rata_gamer.service.publicaciones;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import ar.edu.huergo.rata_gamer.entity.publicaciones.Juego;
import ar.edu.huergo.rata_gamer.repository.publicaciones.JuegoRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - JuegoService")
class JuegoServiceTest{

    @Mock
    private JuegoRepository juegoRepository;

    @InjectMocks
    private JuegoService juegoService;

    private Juego juegoEjemplo;

    @BeforeEach
    void setUp() {
        juegoEjemplo = new Juego();
        juegoEjemplo.setId(1L);
        juegoEjemplo.setNombre("Pokemon HeartGold");
    }

    @Test
    @DisplayName("Debería obtener todos los juegos")
    void deberiaObtenerTodosLosJuegos() {
        // Given
        List<Juego> juegosEsperados = Arrays.asList(juegoEjemplo);
        /*
         * Seteamos que cuando se llame a findAll, se devuelva la lista de Juegos
         * esperados
         */
        when(juegoRepository.findAll()).thenReturn(juegosEsperados);

        // When
        List<Juego> resultado = juegoService.obtenerTodosLosJuegos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(juegoEjemplo.getNombre(), resultado.get(0).getNombre());
        verify(juegoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener juego por ID cuando existe")
    void deberiaObtenerJuegoePorId() {
        // Given
        Long juegoId = 1L;
        when(juegoRepository.findById(juegoId))
                .thenReturn(Optional.of(juegoEjemplo));

        // When
        Juego resultado = juegoService.obtenerJuegoPorID(juegoId);

        // Then
        assertNotNull(resultado);
        assertEquals(juegoEjemplo.getId(), resultado.getId());
        assertEquals(juegoEjemplo.getNombre(), resultado.getNombre());
        verify(juegoRepository, times(1)).findById(juegoId);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando juego no existe")
    void deberiaLanzarExcepcionCuandoJuegoNoExiste() {
        // Given
        Long juegoIdInexistente = 999L;
        when(juegoRepository.findById(juegoIdInexistente)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException excepcion = assertThrows(EntityNotFoundException.class,
                () -> juegoService.obtenerJuegoPorID(juegoIdInexistente));

        assertEquals("Juego no encontrado", excepcion.getMessage());
        verify(juegoRepository, times(1)).findById(juegoIdInexistente);
    }

     @Test
    @DisplayName("Debería crear juego correctamente")
    void deberiaCrearJuego() {
        // Given
        Juego nuevoJuego = new Juego();
        nuevoJuego.setNombre("Pou");

        when(juegoRepository.save(nuevoJuego)).thenReturn(nuevoJuego);

        // When
        Juego resultado = juegoService.crearJuego(nuevoJuego);

        // Then
        assertNotNull(resultado);
        assertEquals(nuevoJuego.getNombre(), resultado.getNombre());
        verify(juegoRepository, times(1)).save(nuevoJuego);
    }

    @Test
    @DisplayName("Debería actualizar juego existente")
    void deberiaActualizarJuego() {
        // Given
        Long juegoId = 1L;
        Juego juegoActualizado = new Juego();
        juegoActualizado.setNombre("The Last Of Us");

        when(juegoRepository.findById(juegoId))
                .thenReturn(Optional.of(juegoEjemplo));
        when(juegoRepository.save(any(Juego.class))).thenReturn(juegoEjemplo);

        // When
        Juego resultado =
                juegoService.actualizarJuego(juegoActualizado, juegoId);

        // Then
        assertNotNull(resultado);
        verify(juegoRepository, times(1)).findById(juegoId);
        verify(juegoRepository, times(1)).save(juegoEjemplo);
        assertEquals(juegoActualizado.getNombre(), juegoEjemplo.getNombre());
    }

    @Test
    @DisplayName("Debería eliminar juego existente")
    void deberiaEliminarJuego() {
        // Given
        Long juegoId = 1L;
        when(juegoRepository.findById(juegoId))
                .thenReturn(Optional.of(juegoEjemplo));
        doNothing().when(juegoRepository).delete(juegoEjemplo);

        // When
        assertDoesNotThrow(() -> juegoService.eliminarJuego(juegoId));

        // Then
        verify(juegoRepository, times(1)).findById(juegoId);
        verify(juegoRepository, times(1)).delete(juegoEjemplo);
    }

    @Test
    @DisplayName("Debería buscar juegos por nombre (case insensitive)")
    void deberiaBuscarJuegosPorNombre() {
        // Given
        String nombreBusqueda = "tom";
        List<Juego> juegosEncontrados = Arrays.asList(juegoEjemplo);
        when(juegoRepository.findByNombreContainingIgnoreCase(nombreBusqueda))
                .thenReturn(juegosEncontrados);

        // When
        List<Juego> resultado =
                juegoService.obtenerJuegosPorNombre(nombreBusqueda);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(juegoEjemplo.getNombre(), resultado.get(0).getNombre());
        verify(juegoRepository, times(1)).findByNombreContainingIgnoreCase(nombreBusqueda);
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no encuentra juegos por nombre")
    void deberiaRetornarListaVaciaCuandoNoEncuentraJuegos() {
        // Given
        String nombreBusqueda = "inexistente";
        when(juegoRepository.findByNombreContainingIgnoreCase(nombreBusqueda))
                .thenReturn(Arrays.asList());

        // When
        List<Juego> resultado =
                juegoService.obtenerJuegosPorNombre(nombreBusqueda);

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(juegoRepository, times(1)).findByNombreContainingIgnoreCase(nombreBusqueda);
    }
}