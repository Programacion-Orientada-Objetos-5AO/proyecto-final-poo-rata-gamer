package ar.edu.huergo.rata_gamer.entity.publicaciones;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación - Entidad PrecioHistorico")
class PrecioHistoricoValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Debería validar un precio histórico con datos válidos")
    void validarPrecioHistoricoConDatosValidos() {
        // Given
        PrecioHistorico precioHistorico = new PrecioHistorico();
        precioHistorico.setFechaInicio(LocalDate.now());
        precioHistorico.setPrecio(BigDecimal.valueOf(59.99));

        // When
        Set<ConstraintViolation<PrecioHistorico>> violations = validator.validate(precioHistorico);

        // Then
        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    @DisplayName("Debería fallar validación con fechaInicio null")
    void deberiaFallarValidacionConFechaInicioNull() {
        // Given
        PrecioHistorico precioHistorico = new PrecioHistorico();
        precioHistorico.setFechaInicio(null);
        precioHistorico.setPrecio(BigDecimal.valueOf(29.99));

        // When
        Set<ConstraintViolation<PrecioHistorico>> violations = validator.validate(precioHistorico);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("fechaInicio")));
    }

    @Test
    @DisplayName("Debería fallar validación con precio null")
    void deberiaFallarValidacionConPrecioNull() {
        // Given
        PrecioHistorico precioHistorico = new PrecioHistorico();
        precioHistorico.setFechaInicio(LocalDate.now());
        precioHistorico.setPrecio(null);

        // When
        Set<ConstraintViolation<PrecioHistorico>> violations = validator.validate(precioHistorico);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("precio")));
    }

    @Test
    @DisplayName("Debería fallar validación con precio con más de 2 decimales")
    void deberiaFallarValidacionConPrecioMasDeDosDecimales() {
        // Given
        PrecioHistorico precioHistorico = new PrecioHistorico();
        precioHistorico.setFechaInicio(LocalDate.now());
        precioHistorico.setPrecio(BigDecimal.valueOf(49.999)); // 3 decimales

        // When
        Set<ConstraintViolation<PrecioHistorico>> violations = validator.validate(precioHistorico);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("precio")));
    }

    @Test
    @DisplayName("Debería aceptar precios con 2 decimales")
    void deberiaAceptarPreciosConDosDecimales() {
        // Given
        PrecioHistorico precioHistorico = new PrecioHistorico();
        precioHistorico.setFechaInicio(LocalDate.now());
        precioHistorico.setPrecio(BigDecimal.valueOf(39.99));

        // When
        Set<ConstraintViolation<PrecioHistorico>> violations = validator.validate(precioHistorico);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Debería aceptar precios enteros")
    void deberiaAceptarPreciosEnteros() {
        // Given
        PrecioHistorico precioHistorico = new PrecioHistorico();
        precioHistorico.setFechaInicio(LocalDate.now());
        precioHistorico.setPrecio(BigDecimal.valueOf(50));

        // When
        Set<ConstraintViolation<PrecioHistorico>> violations = validator.validate(precioHistorico);

        // Then
        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.01, 9.99, 19.99, 49.99, 99.99, 199.99})
    @DisplayName("Debería aceptar precios válidos comunes")
    void deberiaAceptarPreciosValidosComunes(double precio) {
        // Given
        PrecioHistorico precioHistorico = new PrecioHistorico();
        precioHistorico.setFechaInicio(LocalDate.now());
        precioHistorico.setPrecio(BigDecimal.valueOf(precio));

        // When
        Set<ConstraintViolation<PrecioHistorico>> violations = validator.validate(precioHistorico);

        // Then
        assertTrue(violations.isEmpty(), "El precio " + precio + " debería ser válido");
    }

    @Test
    @DisplayName("Debería aceptar fechaFin null")
    void deberiaAceptarFechaFinNull() {
        // Given
        PrecioHistorico precioHistorico = new PrecioHistorico();
        precioHistorico.setFechaInicio(LocalDate.now());
        precioHistorico.setFechaFin(null);
        precioHistorico.setPrecio(BigDecimal.valueOf(29.99));

        // When
        Set<ConstraintViolation<PrecioHistorico>> violations = validator.validate(precioHistorico);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Debería validar precio histórico con constructor completo")
    void deberiaValidarPrecioHistoricoConConstructorCompleto() {
        // Given
        PrecioHistorico precioHistorico = new PrecioHistorico(1L, LocalDate.now(), LocalDate.now().plusDays(30), BigDecimal.valueOf(79.99));

        // When
        Set<ConstraintViolation<PrecioHistorico>> violations = validator.validate(precioHistorico);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Debería aceptar fechas futuras para fechaFin")
    void deberiaAceptarFechasFuturasParaFechaFin() {
        // Given
        PrecioHistorico precioHistorico = new PrecioHistorico();
        precioHistorico.setFechaInicio(LocalDate.now());
        precioHistorico.setFechaFin(LocalDate.now().plusMonths(1));
        precioHistorico.setPrecio(BigDecimal.valueOf(89.99));

        // When
        Set<ConstraintViolation<PrecioHistorico>> violations = validator.validate(precioHistorico);

        // Then
        assertTrue(violations.isEmpty());
    }
}
