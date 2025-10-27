package ar.edu.huergo.rata_gamer.dto.publicaciones;

import java.util.List;

import ar.edu.huergo.rata_gamer.entity.publicaciones.Juego;
import ar.edu.huergo.rata_gamer.entity.publicaciones.Plataforma;
import ar.edu.huergo.rata_gamer.entity.publicaciones.PrecioHistorico;

public record PublicacionDTO(Long id, Juego juego, Plataforma plataforma, List<PrecioHistorico> preciosHistoricos) {
} 