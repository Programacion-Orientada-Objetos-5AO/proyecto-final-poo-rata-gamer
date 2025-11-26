package ar.edu.huergo.rata_gamer.entity;

import java.time.LocalDate;
import java.util.List;

import ar.edu.huergo.rata_gamer.entity.Publicaciones.Publicacion;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "libros")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 500, message = "El nombre debe tener menos de 501 caracteres")
    private String tituloLibro;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 500, message = "El nombre debe tener menos de 501 caracteres")
    private String nombreUsuario;

    @NotNull
    private LocalDate fechaPrestamo;

    @NotNull
    private LocalDate fechaDevolucion;

    @NotNull
    private Boolean devuelto;

    public Libro(String tituloLibro, String nombreUsuario, LocalDate fechaPrestamo, LocalDate fechaDevolucion, Boolean devuelto){
        this.tituloLibro = tituloLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.nombreUsuario = nombreUsuario;
        this.devuelto = devuelto;
    }
}
