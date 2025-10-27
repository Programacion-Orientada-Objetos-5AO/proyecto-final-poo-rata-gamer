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
import java.math.BigDecimal;
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
import ar.edu.huergo.rata_gamer.entity.publicaciones.PrecioHistorico;
import ar.edu.huergo.rata_gamer.repository.publicaciones.PrecioHistoricoRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - PrecioHistoricoService")
class PrecioHistoricoServiceTest{

    @Mock
    private PrecioHistoricoRepository precioHistoricoRepository;

    @InjectMocks
    private PrecioHistoricoService precioHistoricoService;

    private PrecioHistorico precioHistoricoEjemplo;

    @BeforeEach
    void setUp() {
        precioHistoricoEjemplo = new PrecioHistorico();
        precioHistoricoEjemplo.setId(1L);
        precioHistoricoEjemplo.setFechaInicio(LocalDate.of(2023, 1, 1));
        precioHistoricoEjemplo.setFechaFin(LocalDate.of(2023, 12, 31));
        precioHistoricoEjemplo.setPrecio(BigDecimal.valueOf(49.99));
    }

    @Test
    @DisplayName("Debería obtener todos los precios históricos")
    void deberiaObtenerTodosLosPreciosHistoricos() {
        // Given
        List<PrecioHistorico> preciosEsperados = Arrays.asList(precioHistoricoEjemplo);
        when(precioHistoricoRepository.findAll()).thenReturn(preciosEsperados);

        // When
        List<PrecioHistorico> resultado = precioHistoricoService.obtenerTodosLosPrecios();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(precioHistoricoEjemplo.getPrecio(), resultado.get(0).getPrecio());
        verify(precioHistoricoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener precio histórico por ID cuando existe")
    void deberiaObtenerPrecioHistoricoPorId() {
        // Given
        Long precioId = 1L;
        when(precioHistoricoRepository.findById(precioId))
                .thenReturn(Optional.of(precioHistoricoEjemplo));

        // When
        PrecioHistorico resultado = precioHistoricoService.obtenerPrecioHistoricoPorId(precioId);

        // Then
        assertNotNull(resultado);
        assertEquals(precioHistoricoEjemplo.getId(), resultado.getId());
        assertEquals(precioHistoricoEjemplo.getPrecio(), resultado.getPrecio());
        verify(precioHistoricoRepository, times(1)).findById(precioId);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando precio histórico no existe")
    void deberiaLanzarExcepcionCuandoPrecioHistoricoNoExiste() {
        // Given
        Long precioIdInexistente = 999L;
        when(precioHistoricoRepository.findById(precioIdInexistente)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException excepcion = assertThrows(EntityNotFoundException.class,
                () -> precioHistoricoService.obtenerPrecioHistoricoPorId(precioIdInexistente));

        assertEquals("precio historico no encontrado", excepcion.getMessage());
        verify(precioHistoricoRepository, times(1)).findById(precioIdInexistente);
    }

     @Test
    @DisplayName("Debería crear precio histórico correctamente")
    void deberiaCrearPrecioHistorico() {
        // Given
        PrecioHistorico nuevoPrecio = new PrecioHistorico();
        nuevoPrecio.setFechaInicio(LocalDate.of(2024, 1, 1));
        nuevoPrecio.setPrecio(BigDecimal.valueOf(59.99));

        when(precioHistoricoRepository.save(nuevoPrecio)).thenReturn(nuevoPrecio);

        // When
        PrecioHistorico resultado = precioHistoricoService.creaPrecioHistorico(nuevoPrecio);

        // Then
        assertNotNull(resultado);
        assertEquals(nuevoPrecio.getPrecio(), resultado.getPrecio());
        verify(precioHistoricoRepository, times(1)).save(nuevoPrecio);
    }

    @Test
    @DisplayName("Debería crear precio histórico con fechaInicio actual si no se proporciona")
    void deberiaCrearPrecioHistoricoConFechaActual() {
        // Given
        PrecioHistorico nuevoPrecio = new PrecioHistorico();
        nuevoPrecio.setPrecio(BigDecimal.valueOf(39.99));

        when(precioHistoricoRepository.save(any(PrecioHistorico.class))).thenReturn(nuevoPrecio);

        // When
        PrecioHistorico resultado = precioHistoricoService.creaPrecioHistorico(nuevoPrecio);

        // Then
        assertNotNull(resultado);
        verify(precioHistoricoRepository, times(1)).save(any(PrecioHistorico.class));
    }

    @Test
    @DisplayName("Debería actualizar precio completo histórico existente")
    void deberiaActualizarPrecioHistoricoCompleto() {
        // Given
        Long precioId = 1L;
        PrecioHistorico precioActualizado = new PrecioHistorico();
        precioActualizado.setFechaInicio(LocalDate.of(2024, 6, 1));
        precioActualizado.setFechaFin(LocalDate.of(2024, 12, 31));
        precioActualizado.setPrecio(BigDecimal.valueOf(69.99));

        when(precioHistoricoRepository.findById(precioId))
                .thenReturn(Optional.of(precioHistoricoEjemplo));
        when(precioHistoricoRepository.save(any(PrecioHistorico.class))).thenReturn(precioHistoricoEjemplo);

        // When
        PrecioHistorico resultado =
                precioHistoricoService.actualizarPrecioHistorico(precioActualizado, precioId);

        // Then
        assertNotNull(resultado);
        verify(precioHistoricoRepository, times(1)).findById(precioId);
        verify(precioHistoricoRepository, times(1)).save(precioHistoricoEjemplo);
        assertEquals(precioActualizado.getPrecio(), precioHistoricoEjemplo.getPrecio());
        assertEquals(precioActualizado.getFechaInicio(), precioHistoricoEjemplo.getFechaInicio());
        assertEquals(precioActualizado.getFechaFin(), precioHistoricoEjemplo.getFechaFin());
    }

    @Test
    @DisplayName("Debería actualizar solo los campos no nulos de PrecioHistorico")
    void deberiaActualizarPrecioHistoricoParcial() {
        // Given
        Long precioId = 1L;

        // Simula entidad existente en BD
        PrecioHistorico precioEnBD = new PrecioHistorico();
        precioEnBD.setId(precioId);
        precioEnBD.setFechaInicio(LocalDate.of(2024, 1, 1));
        precioEnBD.setFechaFin(LocalDate.of(2024, 6, 30));
        precioEnBD.setPrecio(BigDecimal.valueOf(49.99));

        // Solo se quiere actualizar el precio
        PrecioHistorico precioActualizado = new PrecioHistorico();
        precioActualizado.setPrecio(BigDecimal.valueOf(69.99)); // Solo este campo cambia

        // Mock del repositorio
        when(precioHistoricoRepository.findById(precioId)).thenReturn(Optional.of(precioEnBD));
        when(precioHistoricoRepository.save(any(PrecioHistorico.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // When
        PrecioHistorico resultado = precioHistoricoService.actualizarPrecioHistorico(precioActualizado, precioId);

        // Then
        verify(precioHistoricoRepository).findById(precioId);
        verify(precioHistoricoRepository).save(precioEnBD); // el objeto existente modificado

        assertNotNull(resultado);
        assertEquals(BigDecimal.valueOf(69.99), resultado.getPrecio()); // actualizado
        assertEquals(LocalDate.of(2024, 1, 1), resultado.getFechaInicio()); // no cambiado
        assertEquals(LocalDate.of(2024, 6, 30), resultado.getFechaFin()); // no cambiado
    }


    @Test
    @DisplayName("Debería eliminar precio histórico existente")
    void deberiaEliminarPrecioHistorico() {
        // Given
        Long precioId = 1L;
        when(precioHistoricoRepository.findById(precioId))
                .thenReturn(Optional.of(precioHistoricoEjemplo));
        doNothing().when(precioHistoricoRepository).delete(precioHistoricoEjemplo);

        // When
        assertDoesNotThrow(() -> precioHistoricoService.eliminarPrecioHistoricoPorID(precioId));

        // Then
        verify(precioHistoricoRepository, times(1)).findById(precioId);
        verify(precioHistoricoRepository, times(1)).delete(precioHistoricoEjemplo);
    }

    @Test
    @DisplayName("Debería manejar precios con diferentes valores")
    void deberiaManejarPreciosConDiferentesValores() {
        // Given
        PrecioHistorico precioAlto = new PrecioHistorico();
        precioAlto.setFechaInicio(LocalDate.now());
        precioAlto.setPrecio(BigDecimal.valueOf(99999.99));

        PrecioHistorico precioBajo = new PrecioHistorico();
        precioBajo.setFechaInicio(LocalDate.now());
        precioBajo.setPrecio(BigDecimal.valueOf(0.01));

        // When
        when(precioHistoricoRepository.save(any(PrecioHistorico.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        PrecioHistorico resultadoAlto = precioHistoricoService.creaPrecioHistorico(precioAlto);
        PrecioHistorico resultadoBajo = precioHistoricoService.creaPrecioHistorico(precioBajo);

        // Then
        assertEquals(BigDecimal.valueOf(99999.99), resultadoAlto.getPrecio());
        assertEquals(BigDecimal.valueOf(0.01), resultadoBajo.getPrecio());
    }


}
