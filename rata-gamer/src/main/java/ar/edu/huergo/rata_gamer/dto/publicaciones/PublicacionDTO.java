package ar.edu.huergo.rata_gamer.dto.publicaciones;

import java.util.List;

import ar.edu.huergo.rata_gamer.entity.Publicaciones.Juego;
import ar.edu.huergo.rata_gamer.entity.Publicaciones.Plataforma;
import ar.edu.huergo.rata_gamer.entity.Publicaciones.PrecioHistorico;

public record PublicacionDTO(Long id, Juego juego, Plataforma plataforma, List<PrecioHistorico> preciosHistoricos) {
} 