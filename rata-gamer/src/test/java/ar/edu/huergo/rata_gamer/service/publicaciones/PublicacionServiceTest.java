package ar.edu.huergo.rata_gamer.service.publicaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - PublicacionService (API)")
class PublicacionServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PublicacionService publicacionService;

    private String jsonResponse;
    private List<Map<String, Object>> expectedList;

    @BeforeEach
    void setUp() {
        jsonResponse = "[{\"id\": 1, \"title\": \"Test Giveaway\"}]";
        expectedList = List.of(Map.of("id", 1, "title", "Test Giveaway"));
    }

    @Test
    @DisplayName("Debería obtener publicaciones de la API correctamente")
    void deberiaObtenerPublicacionesDeLaAPICorrectamente() throws JsonProcessingException {
        // Given
        when(restTemplate.getForObject("https://gamerpower.com/api/giveaways", String.class)).thenReturn(jsonResponse);
        when(objectMapper.readValue(any(String.class), any(com.fasterxml.jackson.core.type.TypeReference.class))).thenReturn(expectedList);

        // When
        List<Map<String, Object>> result = publicacionService.obtenerPublicaciones();

        // Then
        assertEquals(expectedList, result);
    }

    @Test
    @DisplayName("Debería parsear respuesta JSON correctamente")
    void deberiaParsearRespuestaJSONCorrectamente() throws JsonProcessingException {
        // Given
        when(objectMapper.readValue(any(String.class), any(com.fasterxml.jackson.core.type.TypeReference.class))).thenReturn(expectedList);

        // When
        List<Map<String, Object>> result = publicacionService.parsearRespuestaJSON(jsonResponse);

        // Then
        assertEquals(expectedList, result);
    }

    @Test
    @DisplayName("Debería lanzar RuntimeException al parsear JSON inválido")
    void deberiaLanzarRuntimeExceptionAlParsearJSONInvalido() throws JsonProcessingException {
        // Given
        String invalidJson = "{invalid}";
        when(objectMapper.readValue(any(String.class), any(com.fasterxml.jackson.core.type.TypeReference.class))).thenThrow(JsonProcessingException.class);

        // When & Then
        assertThrows(RuntimeException.class, () -> publicacionService.parsearRespuestaJSON(invalidJson));
    }
}
