package ar.edu.huergo.rata_gamer.service.publicaciones;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ar.edu.huergo.rata_gamer.dto.publicaciones.JuegoApiDTO;

@Service
public class JuegoApiService {

    private static final String API_URL = "https://www.gamerpower.com/api/giveaways";

    public List<JuegoApiDTO> buscar(String nombre) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                API_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        return response.getBody().stream()
                .filter(j -> ((String) j.get("title")).toLowerCase().contains(nombre.toLowerCase()))
                .map(j -> {
                    JuegoApiDTO dto = new JuegoApiDTO();
                    dto.setNombre((String) j.get("title"));
                    dto.setPrecio((String) j.get("worth"));
                    dto.setPlataforma((String) j.get("platforms"));
                    dto.setImagen((String) j.get("image"));
                    dto.setLink((String) j.get("open_giveaway_url"));
                    return dto;
                })
                .toList();
    }
}
