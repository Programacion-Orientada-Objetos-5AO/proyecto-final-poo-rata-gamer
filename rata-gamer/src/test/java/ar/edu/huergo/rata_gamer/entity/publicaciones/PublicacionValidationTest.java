package ar.edu.huergo.rata_gamer.entity.publicaciones;

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

@DisplayName("Tests de Validación - Entidad Publicacion")
class PublicacionValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Debería validar una publicación con todos los campos válidos")
    void validarPublicacionConCamposValidos() {
        // Given
        Juego juego = new Juego();
        juego.setNombre("The Legend of Zelda");

        Plataforma plataforma = new Plataforma();
        plataforma.setNombre("Nintendo Switch");

        Publicacion publicacion = new Publicacion();
        publicacion.setJuego(juego);
        publicacion.setPlataforma(plataforma);
        publicacion.setPreciosHistoricos(null); // Puede ser null

        // When
        Set<ConstraintViolation<Publicacion>> violations = validator.validate(publicacion);

        // Then
        assertTrue(violations.isEmpty(), "No debería haber violaciones de validación");
    }

    @Test
    @DisplayName("Debería fallar validación con juego null")
    void deberiaFallarValidacionConJuegoNull() {
        // Given
        Plataforma plataforma = new Plataforma();
        plataforma.setNombre("PlayStation 5");

        Publicacion publicacion = new Publicacion();
        publicacion.setJuego(null);
        publicacion.setPlataforma(plataforma);

        // When
        Set<ConstraintViolation<Publicacion>> violaciones = validator.validate(publicacion);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("juego")));
    }

    @Test
    @DisplayName("Debería fallar validación con plataforma null")
    void deberiaFallarValidacionConPlataformaNull() {
        // Given
        Juego juego = new Juego();
        juego.setNombre("FIFA 21");

        Publicacion publicacion = new Publicacion();
        publicacion.setJuego(juego);
        publicacion.setPlataforma(null);

        // When
        Set<ConstraintViolation<Publicacion>> violaciones = validator.validate(publicacion);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("plataforma")));
    }

    @Test
    @DisplayName("Debería fallar validación con juego y plataforma null")
    void deberiaFallarValidacionConJuegoYPlataformaNull() {
        // Given
        Publicacion publicacion = new Publicacion();
        publicacion.setJuego(null);
        publicacion.setPlataforma(null);

        // When
        Set<ConstraintViolation<Publicacion>> violaciones = validator.validate(publicacion);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("juego")));
        assertTrue(violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("plataforma")));
    }

    @Test
    @DisplayName("Debería validar publicación con constructor completo")
    void deberiaValidarPublicacionConConstructorCompleto() {
        // Given
        Juego juego = new Juego();
        juego.setId(1L);
        juego.setNombre("Super Mario");

        Plataforma plataforma = new Plataforma();
        plataforma.setId(1L);
        plataforma.setNombre("Nintendo Switch");

        Publicacion publicacion = new Publicacion();
        publicacion.setId(1L);
        publicacion.setJuego(juego);
        publicacion.setPlataforma(plataforma);
        publicacion.setPreciosHistoricos(null);

        // When
        Set<ConstraintViolation<Publicacion>> violaciones = validator.validate(publicacion);

        // Then
        assertTrue(violaciones.isEmpty());
    }
}
