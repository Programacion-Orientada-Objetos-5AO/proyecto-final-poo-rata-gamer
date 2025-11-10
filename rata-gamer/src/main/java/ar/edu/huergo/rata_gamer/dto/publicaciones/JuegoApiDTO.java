package ar.edu.huergo.rata_gamer.dto.publicaciones;

import lombok.Data;

@Data
public class JuegoApiDTO {
    private String nombre;
    private String precio;
    private String plataforma;
    private String imagen;
    private String link;
}
