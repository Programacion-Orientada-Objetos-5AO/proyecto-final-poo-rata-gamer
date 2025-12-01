package ar.edu.huergo.rata_gamer.dto.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegistrarDTO extends LoginDTO {
        @NotBlank(message = "La verificación de contraseña es requerida")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{16,}$", message = "La verificación de contraseña debe tener al menos 16 caracteres, una mayúscula, una minúscula, un número y un carácter especial")
        private String verificacionPassword;
}