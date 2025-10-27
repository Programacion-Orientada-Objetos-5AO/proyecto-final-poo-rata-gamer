package ar.edu.huergo.rata_gamer.entity.publicaciones;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "juegos")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Juego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 500, message = "El nombre debe tener menos de 501 caracteres")
    private String nombre;

    @OneToMany(mappedBy = "juego")
    private List<Publicacion> publicaciones;

    // @Size(max = 100, message = "la plataforma no puede exceder los 100 caracteres")
    // private List<Plataforma> plataformas;
}
