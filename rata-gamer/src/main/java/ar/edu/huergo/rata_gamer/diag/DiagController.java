package ar.edu.huergo.rata_gamer.diag;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/__diag")
public class DiagController {

  // útil para verificar que pegás al proceso correcto
  @Value("${server.port:8080}")
  private String port;

  @GetMapping("/ping")
  public String ping() {
    return "pong:" + port; // verás el puerto que realmente responde
  }

  // para ver si Postman envía Authorization o headers raros
  @GetMapping("/headers")
  public Map<String, String> headers(@RequestHeader Map<String, String> headers) {
    return headers;
  }

  // para saber qué perfiles están activos (si configuraste alguno)
  @GetMapping("/env")
  public String env(@Value("${spring.profiles.active:default}") String profile) {
    return "profile:" + profile + ";port:" + port;
  }
}

