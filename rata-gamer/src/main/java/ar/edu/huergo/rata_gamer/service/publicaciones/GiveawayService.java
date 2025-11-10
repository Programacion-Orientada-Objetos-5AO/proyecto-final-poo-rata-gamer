package ar.edu.huergo.rata_gamer.service.publicaciones;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ar.edu.huergo.rata_gamer.dto.publicaciones.GiveawayDTO;

@Service
public class GiveawayService {

    private final RestTemplate restTemplate;
    private static final String API_URL = "https://www.gamerpower.com/api/giveaways";

    public GiveawayService() {
        // RestTemplate con timeouts para que no se cuelgue la vista si la API tarda
        SimpleClientHttpRequestFactory f = new SimpleClientHttpRequestFactory();
        f.setConnectTimeout(4000);
        f.setReadTimeout(5000);
        this.restTemplate = new RestTemplate(f);
    }

    public List<GiveawayDTO> obtenerGiveaways() {
        try {
            ResponseEntity<GiveawayDTO[]> resp = restTemplate.getForEntity(API_URL, GiveawayDTO[].class);
            GiveawayDTO[] body = resp.getBody();
            if (body == null) return Collections.emptyList();
            return Arrays.asList(body);
        } catch (Exception e) {
            // En caso de error con la API devolvemos lista vacía para no romper el index
            return Collections.emptyList();
        }
    }

    public List<GiveawayDTO> buscarPorTitulo(String q) {
        if (q == null || q.isBlank()) return obtenerGiveaways();
        final String needle = q.toLowerCase().trim();
        return obtenerGiveaways().stream()
                .filter(g -> g.getTitle() != null && g.getTitle().toLowerCase().contains(needle))
                .collect(Collectors.toList());
    }
}
