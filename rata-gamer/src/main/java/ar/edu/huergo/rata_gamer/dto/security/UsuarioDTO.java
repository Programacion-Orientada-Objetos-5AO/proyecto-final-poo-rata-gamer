package ar.edu.huergo.rata_gamer.dto.security;

import java.util.List;

public record UsuarioDTO(String username, List<String> roles) {
}