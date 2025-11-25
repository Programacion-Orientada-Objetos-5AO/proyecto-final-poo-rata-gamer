package ar.edu.huergo.rata_gamer.entity.publicaciones;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ar.edu.huergo.rata_gamer.entity.Publicaciones.Plataforma;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación - Entidad Plataforma")
class PlataformaValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Debería validar una plataforma con nombre válido")
    void validarPlataformaConNombreValido() {
        // Given
        Plataforma plataforma = new Plataforma();
        plataforma.setNombre("PlayStation 5");

        // When
        Set<ConstraintViolation<Plataforma>> violations = validator.validate(plataforma);

        // Then
        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    @DisplayName("Debería fallar validación con nombre null")
    void deberiaFallarValidacionConNombreNull() {
        // Given
        Plataforma plataforma = new Plataforma();
        plataforma.setNombre(null);

        // When
        Set<ConstraintViolation<Plataforma>> violaciones = validator.validate(plataforma);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre vacío")
    void deberiaFallarValidacionConNombreVacio() {
        // Given
        Plataforma plataforma = new Plataforma();
        plataforma.setNombre("");

        // When
        Set<ConstraintViolation<Plataforma>> violaciones = validator.validate(plataforma);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre solo espacios")
    void deberiaFallarValidacionConNombreSoloEspacios() {
        // Given
        Plataforma plataforma = new Plataforma();
        plataforma.setNombre("   ");

        // When
        Set<ConstraintViolation<Plataforma>> violaciones = validator.validate(plataforma);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre muy largo")
    void deberiaFallarValidacionConNombreMuyLargo() {
        // Given
        String nombreLargo = "P".repeat(51); // 51 caracteres
        Plataforma plataforma = new Plataforma();
        plataforma.setNombre(nombreLargo);

        // When
        Set<ConstraintViolation<Plataforma>> violaciones = validator.validate(plataforma);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getMessage().contains("El nombre debe tener menos de 51 caracteres")));
    }

    @Test
    @DisplayName("Debería aceptar nombres en el límite válido")
    void deberiaAceptarNombresEnLimiteValido() {
        // Given - Nombre de 50 caracteres
        String nombre = "P".repeat(50);
        Plataforma plataforma = new Plataforma();
        plataforma.setNombre(nombre);

        // When
        Set<ConstraintViolation<Plataforma>> violacion = validator.validate(plataforma);

        // Then
        assertTrue(violacion.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"PlayStation 5", "Xbox Series X", "Nintendo Switch", "PC", "Mobile"})
    @DisplayName("Debería aceptar nombres de plataformas comunes")
    void deberiaAceptarNombresDePlataformasReales(String nombreValido) {
        // Given
        Plataforma plataforma = new Plataforma();
        plataforma.setNombre(nombreValido);

        // When
        Set<ConstraintViolation<Plataforma>> violaciones = validator.validate(plataforma);

        // Then
        assertTrue(violaciones.isEmpty(), "El nombre '" + nombreValido + "' debería ser válido");
    }

    @Test
    @DisplayName("Debería aceptar nombres con caracteres especiales válidos")
    void deberiaAceptarNombresConCaracteresEspeciales() {
        // Given - Nombres con espacios, números
        String[] nombresEspeciales = {"PlayStation 4", "Xbox One S", "Nintendo Switch OLED"};

        for (String nombre : nombresEspeciales) {
            Plataforma plataforma = new Plataforma();
            plataforma.setNombre(nombre);

            // When
            Set<ConstraintViolation<Plataforma>> violaciones = validator.validate(plataforma);

            // Then
            assertTrue(violaciones.isEmpty(), "El nombre '" + nombre + "' debería ser válido");
        }
    }

    @Test
    @DisplayName("Debería validar plataforma con constructor completo")
    void deberiaValidarPlataformaConConstructorCompleto() {
        // Given
        Plataforma plataforma = new Plataforma(1L, "Steam", null);

        // When
        Set<ConstraintViolation<Plataforma>> violaciones = validator.validate(plataforma);

        // Then
        assertTrue(violaciones.isEmpty());
    }
}
