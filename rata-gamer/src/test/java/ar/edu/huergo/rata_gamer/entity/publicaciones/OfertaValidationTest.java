package ar.edu.huergo.rata_gamer.entity.publicaciones;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación - Entidad Oferta")
class OfertaValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Debería validar una oferta con datos válidos")
    void validarOfertaConDatosValidos() {
        // Given
        Oferta oferta = new Oferta();
        oferta.setDescripcion("Descuento del 20% en juegos de aventura");
        oferta.setDescuento(20.0);
        oferta.setFechaInicio(LocalDate.now());
        oferta.setFechaFin(LocalDate.now().plusDays(30));

        // When
        Set<ConstraintViolation<Oferta>> violations = validator.validate(oferta);

        // Then
        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    @DisplayName("Debería fallar validación con descuento negativo")
    void deberiaFallarValidacionConDescuentoNegativo() {
        // Given
        Oferta oferta = new Oferta();
        oferta.setDescripcion("Descuento negativo");
        oferta.setDescuento(-10.0);
        oferta.setFechaInicio(LocalDate.now());
        oferta.setFechaFin(LocalDate.now().plusDays(30));

        // When
        Set<ConstraintViolation<Oferta>> violaciones = validator.validate(oferta);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("descuento")));
    }

    @Test
    @DisplayName("Debería fallar validación con fecha fin anterior a fecha inicio")
    void deberiaFallarValidacionConFechaFinAnterior() {
        // Given
        Oferta oferta = new Oferta();
        oferta.setDescripcion("Fechas inválidas");
        oferta.setDescuento(15.0);
        oferta.setFechaInicio(LocalDate.now().plusDays(10));
        oferta.setFechaFin(LocalDate.now());

        // When
        Set<ConstraintViolation<Oferta>> violaciones = validator.validate(oferta);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getMessage().contains("La fecha de fin debe ser posterior a la fecha de inicio")));
    }

    @Test
    @DisplayName("Debería validar oferta con constructor completo")
    void deberiaValidarOfertaConConstructorCompleto() {
        // Given
        Oferta oferta = new Oferta(1L, "Oferta especial", 25.0, LocalDate.now(), LocalDate.now().plusDays(15), null);

        // When
        Set<ConstraintViolation<Oferta>> violaciones = validator.validate(oferta);

        // Then
        assertTrue(violaciones.isEmpty());
    }
}
