package ar.edu.huergo.rata_gamer.service.publicaciones;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.huergo.rata_gamer.entity.Publicaciones.Juego;
import ar.edu.huergo.rata_gamer.entity.Publicaciones.Plataforma;
import ar.edu.huergo.rata_gamer.entity.Publicaciones.PrecioHistorico;
import ar.edu.huergo.rata_gamer.entity.Publicaciones.Publicacion;
import ar.edu.huergo.rata_gamer.repository.publicaciones.JuegoRepository;
import ar.edu.huergo.rata_gamer.repository.publicaciones.PlataformaRepository;
import ar.edu.huergo.rata_gamer.repository.publicaciones.PrecioHistoricoRepository;
import ar.edu.huergo.rata_gamer.repository.publicaciones.PublicacionRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PublicacionService {

    private static final String BASE_KEY = "https://gamerpower.com/api";
    private static final String ENDPOINT = "/giveaways";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private JuegoRepository juegoRepository;

    @Autowired
    private PlataformaRepository plataformaRepository;

    @Autowired
    private PrecioHistoricoRepository precioHistoricoRepository;

    @Autowired
    private JuegoService juegoService;

    @Autowired
    private PlataformaService plataformaService;

    @Autowired
    private PrecioHistoricoService precioHistoricoService;

    public PublicacionService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<Publicacion> obtenerTodosLasPublicaciones(){
        return publicacionRepository.findAll();
    }

    public Publicacion obtenerPublicacionPorId(Long id) throws EntityNotFoundException{
        return publicacionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Publicacion no encontrada"));
    }

    public Publicacion crearPublicacion(Publicacion publicacion){
        return publicacionRepository.save(publicacion);
    }

    public Publicacion actualizarPublicacion(Publicacion publicacion, Long id){
        Publicacion publicacionACambiar = obtenerPublicacionPorId(id);

        for(Field field : publicacionACambiar.getClass().getDeclaredFields()){
            field.setAccessible(true);

            try{
                if(field.get(publicacion) != null){
                    field.set(publicacionACambiar, field.get(publicacion));
                }
            } catch(IllegalAccessException err){
                err.printStackTrace();
            }
        }
        return publicacionRepository.save(publicacionACambiar);
    }

    public void eliminarPublicacion(Long id){
        Publicacion publicacionAEliminar = obtenerPublicacionPorId(id);
        publicacionRepository.delete(publicacionAEliminar);
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

    public void guardarPublicacionDesdeAPI(Map<String, Object> data) {
        // Extraer datos del mapa
        String title = (String) data.get("title");
        String platforms = (String) data.get("platforms");
        String worth = (String) data.get("worth");

        // Crear o obtener Juego
        Juego juego = juegoRepository.findByNombreContainingIgnoreCase(title).stream().findFirst().orElse(null);
        if (juego == null) {
            juego = new Juego();
            juego.setNombre(title);
            juego = juegoService.crearJuego(juego);
        }

        // Crear o obtener Plataforma
        Plataforma plataforma = plataformaRepository.findByNombre(platforms).orElse(null);
        if (plataforma == null) {
            plataforma = new Plataforma();
            plataforma.setNombre(platforms);
            plataforma = plataformaService.crearPlataforma(plataforma);
        }

        // Crear Publicacion
        Publicacion publicacion = new Publicacion();
        publicacion.setJuego(juego);
        publicacion.setPlataforma(plataforma);
        publicacion = publicacionRepository.save(publicacion);

        // Crear PrecioHistorico
        PrecioHistorico precioHistorico = new PrecioHistorico();
        precioHistorico.setFechaInicio(LocalDate.now());
        precioHistorico.setPrecio(parsearPrecio(worth));
        precioHistorico.setPublicacion(publicacion);
        precioHistoricoService.creaPrecioHistorico(precioHistorico);
    }

    private BigDecimal parsearPrecio(String worth) {
        if (worth == null || worth.equals("N/A") || worth.isEmpty()) {
            return BigDecimal.ZERO;
        }
        // Asumiendo formato como "$10.00"
        String precioStr = worth.replace("$", "").trim();
        try {
            return new BigDecimal(precioStr);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }
}
