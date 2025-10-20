package ar.edu.huergo.rata_gamer.entity.publicaciones;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PublicacionService {

    private static final String BASE_KEY = "https://gamerpower.com/api";
    private static final String ENDPOINT = "/giveaways";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public PublicacionService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<Map<String, Object>> obtenerPublicaciones() {
        String url = BASE_KEY + ENDPOINT;
        String response = restTemplate.getForObject(url, String.class);
        return parsearRespuestaJSON(response);
    }

    public List<Map<String, Object>> parsearRespuestaJSON(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }
    }

}
