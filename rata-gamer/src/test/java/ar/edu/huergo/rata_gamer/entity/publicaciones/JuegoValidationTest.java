package ar.edu.huergo.rata_gamer.entity.publicaciones;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación - Entidad Juego")
class JuegoValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Debería validar un juego con nombre válido")
    void validarJuegoConNombreValido() {
        // Given
        Juego juego = new Juego();
        juego.setNombre("The Legend of Zelda");

        // When
        Set<ConstraintViolation<Juego>> violations = validator.validate(juego);
        
        // Then
        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    @DisplayName("Debería fallar validación con nombre null")
    void deberiaFallarValidacionConNombreNull() {
        // Given
        Juego juego = new Juego();
        juego.setNombre(null);

        // When
        Set<ConstraintViolation<Juego>> violaciones = validator.validate(juego);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
        assertTrue(violaciones.stream().anyMatch(v -> v.getMessage().contains("obligatorio")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre vacío")
    void deberiaFallarValidacionConNombreVacio() {
        // Given
        Juego juego = new Juego();
        juego.setNombre("");

        // When
        Set<ConstraintViolation<Juego>> violaciones = validator.validate(juego);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

     @Test
    @DisplayName("Debería fallar validación con nombre solo espacios")
    void deberiaFallarValidacionConNombreSoloEspacios() {
        // Given
        Juego juego = new Juego();
        juego.setNombre("   ");

        // When
        Set<ConstraintViolation<Juego>> violaciones = validator.validate(juego);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre muy largo")
    void deberiaFallarValidacionConNombreMuyLargo() {
        // Given
        String nombreLargo = "J".repeat(501); // 501 caracteres
        Juego juego = new Juego();
        juego.setNombre(nombreLargo);

        // When
        Set<ConstraintViolation<Juego>> violaciones = validator.validate(juego);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getMessage().contains("El nombre debe tener menos de 501 caracteres")));
    }

    @Test
    @DisplayName("Debería aceptar nombres en el límite válido")
    void deberiaAceptarNombresEnLimiteValido() {
        // Given - Nombres de menos de 501 caracteres
        String nombre = "A".repeat(500); // 500 caracteres

        Juego juego = new Juego();
        juego.setNombre(nombre);

        // When
        Set<ConstraintViolation<Juego>> violacion = validator.validate(juego);

        // Then
        assertTrue(violacion.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Super Mario", "Call of Duty", "FIFA 21", "Minecraft", "The Witcher 3", 
    "Grand Theft Auto V"})
    @DisplayName("Debería aceptar nombres de juegos comunes")
    void deberiaAceptarNombresDeJuegosReales(String nombreValido) {
        // Given
        Juego juego = new Juego();
        juego.setNombre(nombreValido);

        // When
        Set<ConstraintViolation<Juego>> violaciones = validator.validate(juego);

        // Then
        assertTrue(violaciones.isEmpty(), "El nombre '" + nombreValido + "' debería ser válido");
    }


    @Test
    @DisplayName("Debería aceptar nombres con caracteres especiales válidos")
    void deberiaAceptarNombresConCaracteresEspeciales() {
        // Given - Nombres con acentos, espacios, guiones
        String[] nombresEspeciales = {"The Binding of isaac: Afterbirth+",/* El mejor juego (GOAT) */
        "Duck Game!", "$Dollar Dash", "#KillAllZombies", "Super Meat Boy+"};

        for (String nombre : nombresEspeciales) {
            Juego juego = new Juego();
            juego.setNombre(nombre);

            // When
            Set<ConstraintViolation<Juego>> violaciones = validator.validate(juego);

            // Then
            assertTrue(violaciones.isEmpty(), "El nombre '" + nombre + "' debería ser válido");
        }
    }

    @Test
    @DisplayName("Debería manejar nombres con números")
    void deberiaManejarNombresConNumeros() {
        // Given
        Juego juego = new Juego();
        juego.setNombre("FIFA 6");

        // When
        Set<ConstraintViolation<Juego>> violaciones = validator.validate(juego);

        // Then
        assertTrue(violaciones.isEmpty(), "Los nombres con números deberían ser válidos");
    }


    @Test
    @DisplayName("Debería validar juego con constructor completo")
    void deberiaValidarJuegoConConstructorCompleto() {
        // Given
        Juego juego = new Juego(1L, "Silksong Zzz", null);

        // When
        Set<ConstraintViolation<Juego>> violaciones = validator.validate(juego);

        // Then
        assertTrue(violaciones.isEmpty());
    }
}