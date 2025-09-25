package ar.edu.huergo.rata_gamer.entity.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación - Entidad Rol")
class RolValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Rol válido - No debe haber violaciones")
    void noDeberiaTenerViolacionesRolValido() {
        Rol rol = new Rol("ADMIN");

        Set<ConstraintViolation<Rol>> violaciones = validator.validate(rol);

        assertTrue(violaciones.isEmpty(), "El rol válido no debería tener violaciones");
    }

    @Test
    @DisplayName("Nombre nulo - Debe haber violación")
    void deberiaFallarValidacionConNombreNull() {
        Rol rol = new Rol(null);

        Set<ConstraintViolation<Rol>> violaciones = validator.validate(rol);

        assertFalse(violaciones.isEmpty(), "El nombre nulo debería generar una violación");
    }

    @Test
    @DisplayName("Nombre con solo espacios - Debe haber violación")
    void deberiaFallarValidacionConNombreSoloEspacios() {
        Rol rol = new Rol("   ");

        Set<ConstraintViolation<Rol>> violaciones = validator.validate(rol);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

    @Test
    @DisplayName("Nombre demasiado largo - Debe haber violación")
    void deberiaFallarValidacionConNombreMuyLargo() {
        String nombreLargo = "A".repeat(51);
        Rol rol = new Rol(nombreLargo);

        Set<ConstraintViolation<Rol>> violaciones = validator.validate(rol);

        assertFalse(violaciones.isEmpty(), "Un nombre con más de 50 caracteres debería generar una violación");
    }

    @Test
    @DisplayName("Nombre demasiado largo - Debe haber violación")
    void deberiaAceptarNombresEnLimiteValido() {
        String nombreLargo = "A".repeat(50);
        Rol rol = new Rol(nombreLargo);

        Set<ConstraintViolation<Rol>> violaciones = validator.validate(rol);

        assertTrue(violaciones.isEmpty());
    }
}
