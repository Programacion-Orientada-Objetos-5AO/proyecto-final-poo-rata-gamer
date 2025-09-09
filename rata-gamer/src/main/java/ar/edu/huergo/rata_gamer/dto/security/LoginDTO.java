package ar.edu.huergo.rata_gamer.dto.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class LoginDTO {
        @NotBlank(message = "El nombre de usuario es requerido")
        @Email(message = "El nombre de usuario debe ser un email válido")
        private String username;
        @NotBlank(message = "La contraseña es requerida") 
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{16,}$", message = "La contraseña debe tener al menos 16 caracteres, una mayúscula, una minúscula, un número y un carácter especial")
        private String password; 
}